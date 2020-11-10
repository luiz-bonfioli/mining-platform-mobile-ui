package br.com.mining.mobile.service

import android.annotation.SuppressLint
import android.util.Log
import br.com.mining.mobile.data.model.InboundEntity
import br.com.mining.mobile.data.model.TransactionEntity
import br.com.mining.mobile.service.messaging.MqttChannelImpl.publish
import br.com.mining.mobile.service.messaging.MqttChannelImpl.subscribe
import br.com.mining.mobile.service.messaging.MqttChannelImpl.unsubscribe
import br.com.mining.mobile.service.synchronism.TransactionCreatedRequest
import br.com.mining.mobile.service.synchronism.TransactionRequest
import br.com.mining.mobile.shared.builders.TopicBuilder
import br.com.mining.mobile.shared.enums.MachineStatus
import br.com.mining.mobile.shared.enums.TransactionState
import br.com.mining.mobile.shared.enums.TransactionType
import br.com.mining.mobile.shared.model.Inbound
import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.mobile.shared.repository.InboundRepository
import br.com.mining.mobile.shared.repository.TransactionRepository
import br.com.mining.mobile.shared.service.ImportService
import br.com.mining.mobile.shared.synchronism.SyncModelState
import br.com.mining.mobile.shared.synchronism.SyncState
import br.com.mining.mobile.shared.synchronism.enums.SyncModelStatus
import br.com.mining.mobile.shared.synchronism.enums.SyncStatus
import br.com.mining.mobile.shared.utils.UUIDUtils
import br.com.mining.platform.shared.ConnectivityManager
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.nio.ByteBuffer
import java.util.*

@SuppressLint("CheckResult")
class ImportServiceImpl : ImportService, KoinComponent {

    private val TAG = "ImportMachine"

    private val LIMIT_NACK = 5
    private val subjectMap: MutableMap<String, PublishSubject<SyncModelState>> = HashMap()
    private val nackMap: MutableMap<String, Int> = HashMap()
    private var observableSync: PublishSubject<SyncState>? = null

    val transactionRepository: TransactionRepository by inject()

    override fun addSubscriber(subscriber: Consumer<SyncState>): Disposable? {
        observableSync = PublishSubject.create<SyncState>()
        observableSync?.subscribeOn(Schedulers.newThread())
        observableSync?.observeOn(AndroidSchedulers.mainThread())
        return observableSync?.subscribe(subscriber)
    }

    override fun importData(
        topic: String,
        payload: ByteArray,
        mostCurrent: Boolean,
        subscriber: Consumer<SyncModelState>
    ) {
        subscribeConsumers(topic, subscriber)
        checkTransaction(topic, payload, mostCurrent)
    }

    override fun onProcess(message: ByteArray, topic: String) {
        val buffer = ByteBuffer.wrap(message)
        val eventId = buffer.get()
        val content = ByteArray(buffer.remaining())
        buffer[content]
        when (eventId) {
            Protocol.Event.FRAGMENTS_AVAILABLE -> onInitTransaction(content)
            Protocol.Event.CREATE_TRANSACTION -> onUpdateTransaction(content)
//            Protocol.Event.FRAGMENT_RESPONSE -> onFragmentResponse(content)
            Protocol.Event.ABORT_TRANSACTION -> onAbortTransaction(content)
            Protocol.Event.EMPTY_TRANSACTION -> onEmptyTransaction(content)
//            Protocol.Event.ERROR -> onError(content, topic)
            else -> Log.d(TAG, "ERROR  onProcess")
        }
    }

    private fun onError(content: ByteArray, topic: String) {
        var topic = topic
        val buffer = ByteBuffer.wrap(content)
        val messageArray = ByteArray(buffer.remaining())
        buffer[messageArray]
        topic = topic.replace("/response", "")
        unsubscribe(topic)
        val syncModelState = SyncModelState(SyncModelStatus.ERROR)
        syncModelState.topic = topic
        notify(topic, syncModelState)
    }


    override fun resume() {
        val connectivityManager: ConnectivityManager by inject()
        if (connectivityManager.isMqttOnline) {
            Log.d(TAG, " << RESUME SYNCRONISM IMPORT >> ")
            Single.fromCallable {
                val source: TransactionRepository by inject()
                val transactions: List<Transaction> =
                    source.fecthTransaction(TransactionState.CREATED)
                for (transaction in transactions) {
                    continueTransaction(transaction)
                }
            }.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .doOnError {
                    it.printStackTrace()
                }
                .subscribe()

        }
    }

    override fun abort(topic: String) {
        Single.fromCallable {
            val source: TransactionRepository by inject()
            val transaction = source.getByTopicNotComplete(topic)
            transaction?.let {
                onAbort(transaction.topic, transaction.fecthTransactionId().toString())
            }
            transaction
        }.subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun pause() {
        Log.d(TAG, " << PAUSE SYNCRONISM IMPORT >> ")
        Single.fromCallable {
            val source: TransactionRepository by inject()
            val transactions: List<Transaction> = source.fecthTransaction(TransactionState.CREATED)
            for (transaction in transactions) {
                unsubscribe(transaction.topic + Protocol.Topic.RESPONSE)
            }

        }.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.newThread())
            .subscribe()
    }

    override fun startTransaction(transaction: Transaction) {
        Single.fromCallable {
            continueTransaction(transaction)
            transaction
        }.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.newThread())
            .subscribe()
    }

    override fun importAttachment(topic: String, payload: ByteArray, subscriber: Consumer<SyncModelState>) {
        register(topic, subscriber)
        subscribe(topic + Protocol.Topic.RESPONSE)
        publish(payload, topic + Protocol.Topic.REQUEST)
    }

    override fun resumeAttachment(
        topic: String,
        transaction: Transaction,
        subscriber: Consumer<SyncModelState>
    ) {
        register(topic, subscriber)
        continueTransaction(transaction)
    }

    override fun register(topic: String, subscriber: Consumer<SyncModelState>) {
        subscribeConsumers(topic, subscriber)
    }

    private fun continueTransaction(transaction: Transaction) {
        val source: InboundRepository by inject()
        val inbound = source.findByLastInbound(transaction.fecthTransactionId().toString())
        inbound?.let {
            subscribe(transaction.topic + Protocol.Topic.RESPONSE)
            sendAck(
                transaction.topic, transaction.fecthTransactionId(),
                UUID.fromString(inbound.id)
            )
        } ?: run {
            val transactionRepository: TransactionRepository by inject()
            transactionRepository.delete(transaction)
        }
    }

    private fun subscribeConsumers(topic: String, subscriber: Consumer<SyncModelState>) {
        val observable: PublishSubject<SyncModelState> = PublishSubject.create<SyncModelState>()
        observable.subscribeOn(Schedulers.newThread())
        observable.observeOn(AndroidSchedulers.mainThread())
        observable.subscribe(subscriber)
        subjectMap[topic] = observable
    }

    private fun checkTransaction(topic: String, payload: ByteArray, mostCurrent: Boolean) {
        Single.fromCallable {
            val transactionRepository: TransactionRepository by inject()
            val transaction = transactionRepository.getByTopicNotComplete(topic)
            transaction?.let {
                createTransaction(topic, payload, mostCurrent)
            } ?: run {
                if (mostCurrent) {
                    resetTransaction(topic, payload, mostCurrent)
                } else {
                    when (transaction?.status) {
                        TransactionState.FINISH -> finishTransaction(
                            transaction.topic, transaction
                                .fecthTransactionId().toString(), 0, 0
                        )
                        TransactionState.CREATED, TransactionState.CREATING -> resetTransaction(
                            topic,
                            payload,
                            mostCurrent
                        )
                        else -> transaction?.let { continueTransaction(it) }
                    }
                }
            }
        }.subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                it.printStackTrace()
                Log.d(TAG, "Error : checkTransaction")
            })
    }

    private fun resetTransaction(topic: String, payload: ByteArray, mostCurrent: Boolean) {
        transactionRepository.destroyTransaction(topic) { }
        createTransaction(topic, payload, mostCurrent)
    }

    private fun createTransaction(topic: String, payload: ByteArray, isPersist: Boolean) {
        val transactionNew: Transaction = TransactionEntity(
            UUID.randomUUID().toString(),
            topic, TransactionState.CREATING, TransactionType.IMPORT, null,
            null, 0, Date().time
        )
        transactionRepository.insert(transactionNew)
        subscribe(topic + Protocol.Topic.RESPONSE)
        publish(payload, topic + Protocol.Topic.REQUEST)
    }

    private fun onUpdateTransaction(message: ByteArray) {
        val buffer = ByteBuffer.wrap(message)
        val transactionId: UUID = UUIDUtils.generationUUID(buffer)
        val content = ByteArray(buffer.remaining())
        buffer[content]
        val topic: String = TopicBuilder.buildUserTopic(String(content)).replace(".", "/")
        Single.fromCallable<Any> {
            val transaction = transactionRepository.getByTopic(topic, TransactionState.CREATING)
            val transactionNew: Transaction = TransactionEntity(
                transactionId.toString(),
                topic, TransactionState.CREATED, TransactionType.IMPORT, null, null, 0,
                Date().time)
            transactionRepository.insert(transactionNew)
            if (transaction != null) {
                transactionRepository.delete(transaction)
            }
            transactionNew
        }.subscribeOn(Schedulers.io())
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                Log.d(TAG, "******* Waiting for an answer *******")
            }, { e: Throwable ->
                e.printStackTrace()
                val syncModelState = SyncModelState(SyncModelStatus.ERROR)
                syncModelState.transactionId = transactionId.toString()
                notify(topic, syncModelState)
            })
    }

    private fun onEmptyTransaction(message: ByteArray) {
        val buffer = ByteBuffer.wrap(message)
        val content = ByteArray(buffer.remaining())
        buffer[content]
        val topic: String = TopicBuilder.buildUserTopic(String(content))
            .replace(".", "/")
        Single.fromCallable<Any?> {
            val transaction = transactionRepository.getByTopicNotComplete(topic)
            if (transaction != null) {
                transaction.status = TransactionState.COMPLETE
                transactionRepository.update(transaction)
            }
            transaction
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                executeEmpty(topic)
            }, { e: Throwable ->
                e.printStackTrace()
                notify(topic, SyncModelState(SyncModelStatus.ERROR))
            })
    }

    private fun onInitTransaction(message: ByteArray) {
        Single.fromCallable<Transaction> {
            val buffer = ByteBuffer.wrap(message)
            val transactionId: UUID = UUIDUtils.generationUUID(buffer)
            val packegeNumber = buffer.int
            val transaction: Transaction? = transactionRepository.getById(transactionId.toString())
            transaction?.let {
                transaction.packageNumber = packegeNumber
                transactionRepository.update(transaction)
            }
            transaction
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ transaction: Transaction ->
                requestNextFrament(transaction.topic, transaction.fecthTransactionId())
            }, { e: Throwable ->
                e.printStackTrace()
            })
    }

    private fun requestNextFrament(topic: String, transactionId: UUID) {
        val transactionRequest = TransactionCreatedRequest(
            Protocol.Service.TRANSACTION, Protocol.Event.EXPORT,
            Protocol.Event.FRAGMENT_REQUEST, transactionId
        )
        publish(transactionRequest.payload(), topic + Protocol.Topic.REQUEST)
        notify(topic, SyncModelState(SyncModelStatus.CREATE))
    }

    private fun onAbortTransaction(message: ByteArray) {
        val buffer = ByteBuffer.wrap(message)
        val transactionId: UUID = UUIDUtils.generationUUID(buffer)
        Single.fromCallable {
            val transaction: Transaction? = transactionRepository.getById(transactionId.toString())
            onAbort(transaction?.topic ?: "", transactionId.toString())
            transaction
        }.subscribeOn(Schedulers.io())
            .subscribe({
            }, { e: Throwable ->
                e.printStackTrace()
            })
    }

    private fun onFragmentResponse(message: ByteArray) {
        val buffer = ByteBuffer.wrap(message)
        val transactionId: UUID = UUIDUtils.generationUUID(buffer)
        val outboundId: UUID = UUIDUtils.generationUUID(buffer)
        val packageNumber = buffer.int
        val numberOfPackage = buffer.int
        val content = ByteArray(buffer.remaining())
        buffer[content]
        saveFragment(transactionId, outboundId, packageNumber, numberOfPackage, content)
    }

    private fun saveFragment(
        transactionId: UUID, outboundId: UUID, packageNumber: Int,
        numberOfPackage: Int, content: ByteArray
    ) {
        Single.fromCallable {
            val inbound: Inbound = InboundEntity(
                outboundId.toString(), MachineStatus.OPEN,
                transactionId.toString(), packageNumber, content
            )
            val source: InboundRepository by inject()
            source.insert(inbound)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ result ->
                onNextFragment(
                    transactionId, outboundId, packageNumber,
                    numberOfPackage, result > 0
                )
            }, { e: Throwable ->
                e.printStackTrace()
                onNextFragment(transactionId, outboundId, packageNumber, numberOfPackage, false)
            })
    }

    private fun onNextFragment(
        transactionId: UUID, outboundId: UUID, packageNumber: Int,
        numberOfPackage: Int, isAck: Boolean
    ) {
        Single.fromCallable {
            transactionRepository.getById(transactionId.toString())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ transaction ->
                transaction?.let {
                    val topic: String = transaction.topic
                    if (transaction.isValid()) {
                        if (isAck) {
                            sendAck(topic, transactionId, outboundId)
                        } else if (isAbort(topic)) {
                            nackMap.remove(transactionId.toString())
                            sendAbort(topic, transactionId, outboundId)
                            onAbort(topic, transactionId.toString())
                        } else {
                            saveNack(topic)
                            sendNack(topic, transactionId, outboundId)
                        }
                        finishTransaction(
                            topic, transactionId.toString(), packageNumber,
                            numberOfPackage
                        )
                    }
                }
            }, { e: Throwable ->
                e.printStackTrace()
            })
    }

    private fun finishTransaction(
        topic: String, transactionId: String, packageNumber: Int,
        numberOfPackage: Int
    ) {
        val syncModelState: SyncModelState
        if (isFinished(packageNumber, numberOfPackage)) {
            unsubscribe(topic + Protocol.Topic.RESPONSE)
            syncModelState = SyncModelState(SyncModelStatus.FINISH)
            transactionRepository.uploadTransaction(
                transactionId,
                TransactionState.FINISH
            ) { }
        } else {
            syncModelState = SyncModelState(SyncModelStatus.RESPONSE_ACK)
        }
        syncModelState.topic = topic
        syncModelState.transactionId = transactionId
        syncModelState.numberOfPackage = numberOfPackage
        syncModelState.packageNumber = packageNumber
        notify(topic, syncModelState)
    }

    private fun notify(topic: String, syncModelState: SyncModelState) {
        if (subjectMap.containsKey(topic)) {
            subjectMap[topic]?.onNext(syncModelState)
        }
        when (syncModelState.modelStatus) {
            SyncModelStatus.EMPTY -> observableSync!!.onNext(
                SyncState(
                    syncModelState.transactionId,
                    SyncStatus.EMPTY, topic, TransactionType.IMPORT,
                    syncModelState.packageNumber, syncModelState.numberOfPackage
                )
            )
            SyncModelStatus.FINISH -> observableSync?.onNext(
                SyncState(
                    syncModelState.transactionId,
                    SyncStatus.FINISH, topic, TransactionType.IMPORT,
                    syncModelState.packageNumber, syncModelState.numberOfPackage
                )
            )
            SyncModelStatus.ERROR -> {
                val id = if (syncModelState.transactionId.isEmpty()) UUID.randomUUID()
                    .toString() else syncModelState.transactionId
                observableSync?.onNext(
                    SyncState(
                        id, SyncStatus.ERROR, topic,
                        TransactionType.IMPORT, -1, -1
                    )
                )
            }
            else -> observableSync?.onNext(
                SyncState(
                    syncModelState.transactionId,
                    SyncStatus.PROCESSING, topic, TransactionType.IMPORT,
                    syncModelState.packageNumber, syncModelState.numberOfPackage
                )
            )
        }
    }

    private fun sendNack(topic: String, transactionId: UUID, outboundId: UUID) {
        val transactionRequest = TransactionRequest(
            Protocol.Service.TRANSACTION,
            Protocol.Event.EXPORT,
            Protocol.Event.NACK,
            transactionId,
            outboundId
        )
        publish(transactionRequest.payload(), topic + Protocol.Topic.REQUEST)
    }

    private fun sendAck(topic: String, transactionId: UUID, outboundId: UUID) {
        val transactionRequest = TransactionRequest(
            Protocol.Service.TRANSACTION,
            Protocol.Event.EXPORT,
            Protocol.Event.ACK,
            transactionId,
            outboundId
        )
        publish(transactionRequest.payload(), topic + Protocol.Topic.REQUEST)
    }

    private fun sendAbort(topic: String, transactionId: UUID, outboundId: UUID) {
        val transactionRequest = TransactionRequest(
            Protocol.Service.TRANSACTION,
            Protocol.Event.EXPORT,
            Protocol.Event.ABORT,
            transactionId,
            outboundId
        )
        publish(transactionRequest.payload(), topic + Protocol.Topic.REQUEST)
    }

    private fun onAbort(topic: String, transactionId: String) {
        unsubscribe(topic + Protocol.Topic.RESPONSE)
        val syncModelState = SyncModelState(SyncModelStatus.ABORT)
        syncModelState.topic = topic
        syncModelState.transactionId = transactionId
        notify(topic, syncModelState)
        Single.fromCallable<Any> {
            val transaction = transactionRepository.getById(transactionId)
            transaction?.status = TransactionState.COMPLETE
            transaction?.let { transactionRepository.update(it) }
            transaction
        }.subscribeOn(Schedulers.io())
            .subscribe({
            }, { e: Throwable ->
                e.printStackTrace()
            })
    }

    private fun isAbort(topic: String): Boolean {
        return if (nackMap.containsKey(topic)) {
            nackMap[topic]!! > LIMIT_NACK
        } else false
    }

    private fun saveNack(topic: String) {
        var amount = 1
        if (nackMap.containsKey(topic)) {
            amount += nackMap[topic]!!
            nackMap.remove(topic)
        }
        nackMap[topic] = amount
    }

    private fun isFinished(packageNumber: Int, numberOfPackage: Int): Boolean {
        return packageNumber == numberOfPackage
    }

    private fun executeEmpty(topic: String) {
        unsubscribe(topic + Protocol.Topic.RESPONSE)
        notify(topic, SyncModelState(SyncModelStatus.EMPTY))
    }


}