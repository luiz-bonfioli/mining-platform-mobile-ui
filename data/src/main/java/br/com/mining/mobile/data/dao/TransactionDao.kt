package br.com.mining.mobile.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.mining.mobile.data.model.TransactionEntity

@Dao
interface TransactionDao : DaoBase<TransactionEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }

    @Query("SELECT * FROM `transaction` WHERE `transaction`.id = :transactionId")
    fun getById(transactionId: String): TransactionEntity?

    @Query("SELECT * FROM `transaction` WHERE `transaction`.topic = :topic and `transaction`.status != :status LIMIT 1")
    fun getByTopicNotComplete(topic: String, status: TransactionState = TransactionState.COMPLETE): TransactionEntity?

    @Query("SELECT * FROM `transaction` WHERE `transaction`.status = :status")
    fun fecthTransaction(status: TransactionState): List<TransactionEntity>

    @Query("SELECT * FROM `transaction` WHERE `transaction`.topic = :topic LIMIT 1")
    fun getByTopic(topic: String): TransactionEntity?

    @Query("SELECT * FROM `transaction` WHERE `transaction`.topic = :topic and status = :status LIMIT 1")
    fun getByTopic(topic: String, status: TransactionState): TransactionEntity?

    @Query("SELECT * FROM `transaction` WHERE `transaction`.type = :type and `transaction`.status = :status")
    fun getByTypeAndStatus(type: TransactionType, status: TransactionState): List<TransactionEntity>

    @Query("SELECT * FROM `transaction` WHERE `transaction`.type = :type")
    fun fecthByType(type: TransactionType): List<TransactionEntity>?

    @Query("SELECT * FROM `transaction` WHERE `transaction`.type = :type and `transaction`.status != :status")
    fun fecthByTypeNotFinish(type: TransactionType, status: TransactionState = TransactionState.FINISH): List<TransactionEntity>?

    @Query("SELECT * FROM `transaction` WHERE `transaction`.type = :type and `transaction`.status == :status")
    fun fecthByCreated(type: TransactionType, status: TransactionState = TransactionState.CREATED): List<TransactionEntity>?

    @Query("SELECT COUNT(*) FROM `transaction` WHERE topic = :topic and `transaction`.status != :status ")
    fun countTopic(topic: String, status: TransactionState = TransactionState.COMPLETE): Long

    @Query("SELECT * FROM `transaction` ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): MutableList<TransactionEntity>

    @Query("SELECT * FROM `transaction` WHERE `transaction`.status != :status")
    fun fecthNotFinish(status: TransactionState = TransactionState.FINISH): List<TransactionEntity>?

    @Query("DELETE FROM `transaction` where createdAt < :date and status = :status")
    fun deleteBeforeByDate(date: Long, status: TransactionState = TransactionState.COMPLETE)

    @Query("DELETE FROM `transaction` where status = :status")
    fun deleteAllSuccess(status: TransactionState = TransactionState.COMPLETE)
}