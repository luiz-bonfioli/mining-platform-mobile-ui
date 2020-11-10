package br.com.mining.mobile.service.synchronism

import android.annotation.SuppressLint
import android.util.Log
import br.com.mining.mobile.shared.enums.TransactionType
import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.mobile.shared.repository.TransactionRepository
import br.com.mining.mobile.shared.service.ImportService
import br.com.mining.mobile.shared.synchronism.RequestHeader
import br.com.mining.mobile.shared.synchronism.SyncModelState
import br.com.mining.mobile.shared.synchronism.SyncState
import br.com.mining.mobile.shared.synchronism.Synchronism
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class SynchronismImpl : Synchronism, KoinComponent {

    private val TAG = "SYNC"
    private var isResume = false
    private val mObservable: PublishSubject<SyncState> = PublishSubject.create()

    val importService: ImportService by inject()

     init {
        mObservable.delay(500L, TimeUnit.MILLISECONDS)
        mObservable.subscribeOn(Schedulers.newThread())
        mObservable.observeOn(AndroidSchedulers.mainThread())
        importService.addSubscriber( object : Consumer<SyncState> {
            override fun accept(syncState: SyncState) {
                mObservable.onNext(syncState)
            }
        })
    }

    override fun addSubscriber(subscriber: Consumer<SyncState>): Disposable {
        return mObservable.subscribe(subscriber)
    }

    override fun pause() {
        Log.d(TAG, "pause")
        if (isResume) {
            pauseMachine()
            isResume = false
        }
    }

    override fun resume() {
        Log.d(TAG, "resume")
        if (!isResume) {
            resumeMachine()
            isResume = true
        }
    }

    override fun reset() {
        pause()
        resume()
    }

    override fun <T : RequestHeader> importData(topic: String, request: T, requireNewTransaction: Boolean,
                                                 subscriber: Consumer<SyncModelState>) {
        importService.importData(topic, request.payload, requireNewTransaction, subscriber)
    }

    override fun register(topic: String, subscriber: Consumer<SyncModelState>) {
        importService.register(topic, subscriber)
    }

    override fun exportData(transaction: Transaction) {
    }

    override fun resumeTransaction(transaction: Transaction) {
        if (transaction.type === TransactionType.IMPORT) {
            importService.startTransaction(transaction)
        }
    }

    override fun importAttachment(topic: String, payload: ByteArray,
                                  subscriber: Consumer<SyncModelState>) {
        importService.importAttachment(topic, payload, subscriber)
    }

    override fun resumeAttachment(topic: String, transaction: Transaction,
                                  subscriber: Consumer<SyncModelState>) {
        importService.resumeAttachment(topic, transaction, subscriber)
    }

    override fun onMessageArrived(message: ByteArray, eventId: Byte, topic: String) {
        Log.d(TAG, " ******* SYNC HAS ARRIVED ****** $eventId")
        when (eventId) {
            Protocol.Event.IMPORT ->
                importService.onProcess(message, topic)
            else ->
                Log.d(TAG, " ******* ERROR SYNC EVENT:****** $eventId")
        }
    }

    private fun pauseMachine() {
        importService.pause()
    }

    private fun resumeMachine() {
        importService.resume()
    }
}