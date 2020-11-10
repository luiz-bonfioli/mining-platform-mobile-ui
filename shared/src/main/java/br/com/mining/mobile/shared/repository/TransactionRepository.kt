package br.com.mining.mobile.shared.repository

import br.com.mining.mobile.shared.enums.TransactionState
import br.com.mining.mobile.shared.enums.TransactionType
import br.com.mining.mobile.shared.model.Transaction


interface TransactionRepository : RepositoryBase<Transaction> {

    fun getByTopicNotComplete(topic: String): Transaction?

    fun fecthTransaction(status: TransactionState): List<Transaction>

    fun getByTopic(topic: String, status: TransactionState): Transaction?

    fun getByTypeAndStatus(type: TransactionType, status: TransactionState): List<Transaction>

    fun countTopic(topic: String): Long

    fun fecthByAll(limit: Int, offset: Int): List<Transaction>?

    fun uploadTransaction(transactionId: String, status: TransactionState,
                          result: (resp: Boolean) -> Unit)

    fun destroyTransaction(topic: String, result: (resp: Boolean) -> Unit)

    fun destroyTransaction(transaction: Transaction, result: () -> Unit)

    fun fecthByType(type: TransactionType): List<Transaction>?

    fun fecthByTypeNotFinish(type: TransactionType): List<Transaction>?

    fun fecthByCreated(type: TransactionType): List<Transaction>?

    fun getfecthNotFinish(): List<Transaction>?

    fun deleteBeforeByDate(date: Long)

    fun deleteAllSuccess()
}