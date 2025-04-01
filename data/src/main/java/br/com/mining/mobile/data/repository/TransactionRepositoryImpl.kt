package br.com.mining.mobile.data.repository

import android.annotation.SuppressLint
import br.com.mining.mobile.data.dao.TransactionDao
import br.com.mining.mobile.data.model.TransactionEntity
import br.com.mining.mobile.shared.enums.TransactionState
import br.com.mining.mobile.shared.enums.TransactionType
import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.mobile.shared.repository.TransactionRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressLint("CheckResult")
class TransactionRepositoryImpl : RepositoryBaseImpl<TransactionDao, TransactionEntity, Transaction>(),
    TransactionRepository, KoinComponent {

    override val dao: TransactionDao by inject()

    override fun getByTopicNotComplete(topic: String): Transaction? =
        dao.getByTopicNotComplete(topic)

    override fun fecthTransaction(status: TransactionState): List<Transaction> =
        dao.fecthTransaction(status)


    override fun getById(id: String): Transaction? = dao.getById(id)

    override fun getByTopic(topic: String, status: TransactionState): Transaction? =
        dao.getByTopic(topic, status)

    override fun getByTypeAndStatus(
        type: TransactionType,
        status: TransactionState
    ): List<Transaction> =
        dao.getByTypeAndStatus(type, status)

    override fun countTopic(topic: String): Long = dao.countTopic(topic)

    override fun fecthByAll(limit: Int, offset: Int): List<Transaction>? = dao.getAll(limit, offset)

    override fun deleteBeforeByDate(date: Long) = dao.deleteBeforeByDate(date)

    override fun deleteAllSuccess() = dao.deleteAllSuccess()

    override fun fecthByType(type: TransactionType): List<Transaction>? = dao.fecthByType(type)

    override fun fecthByTypeNotFinish(type: TransactionType): List<Transaction>? =
        dao.fecthByTypeNotFinish(type)

    override fun fecthByCreated(type: TransactionType): List<Transaction>? =
        dao.fecthByCreated(type)

    override fun getfecthNotFinish(): List<Transaction>? = dao.fecthNotFinish()

    override fun uploadTransaction(
        transactionId: String, status: TransactionState,
        result: (resp: Boolean) -> Boolean
    ) {
        Single.fromCallable {
            val transaction = getById(transactionId)
            transaction?.let {
//                it.status = status
                update(it)
                true
            }
            false
        }.subscribeOn(Schedulers.io())
            .subscribe({
                result(it)
            }, {
                result(false)
            })
    }

    override fun destroyTransaction(topic: String, result: (resp: Boolean) -> Unit) {
        Single.fromCallable {
            val transaction = getByTopicNotComplete(topic)
            transaction?.let {
//                Injector.get(InboundDataSource::class.java).findByTransaction(it.fecthTransactionId().toString())?.forEach {
//                    Injector.get(InboundDataSource::class.java).delete(it)
//                }
//                Injector.get(OutboundDataSource::class.java).findByTransaction(transaction.id)?.forEach {
//                    Injector.get(OutboundDataSource::class.java).delete(it)
//                }
                delete(it)
                return@fromCallable true
            } ?: false
        }.subscribeOn(Schedulers.io())
            .subscribe({
                result(true)
            }, {
                it.printStackTrace()
                result(false)
            })
    }

    override fun destroyTransaction(transaction: Transaction, result: () -> Unit) {
        Single.fromCallable {
            transaction.let {
//                Injector.get(InboundDataSource::class.java).findByTransaction(it.fecthTransactionId().toString())?.forEach {
//                    Injector.get(InboundDataSource::class.java).delete(it)
//                }
//                Injector.get(OutboundDataSource::class.java).findByTransaction(transaction.id)?.forEach {
//                    Injector.get(OutboundDataSource::class.java).delete(it)
//                }
                delete(it)
            }
        }.subscribeOn(Schedulers.io())
            .subscribe({
                result()
            }, {
                it.printStackTrace()
                result()
            })
    }

}