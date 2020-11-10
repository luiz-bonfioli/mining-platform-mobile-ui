package br.com.mining.mobile.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.mining.mobile.data.model.InboundEntity
import br.com.mining.mobile.shared.enums.MachineStatus

@Dao
interface InboundDao : DaoBase<InboundEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }

    @Query("SELECT * FROM `inbound` WHERE `inbound`.id = :inboundId")
    fun getById(inboundId: String): InboundEntity?

    @Query("SELECT * FROM `inbound` WHERE `inbound`.transactionId = :transactionId and status = :status LIMIT 1")
    fun getByTransaction(transactionId: String, status: MachineStatus): InboundEntity?

    @Query("SELECT * FROM `inbound` WHERE `inbound`.transactionId = :transactionId and packageNumber = :number LIMIT 1")
    fun getByTransaction(transactionId: String, number: Int): InboundEntity?

    @Query("SELECT * FROM `inbound` WHERE `inbound`.transactionId = :transactionId")
    fun getByTransaction(transactionId: String): List<InboundEntity>?

    @Query("DELETE FROM `inbound` WHERE `inbound`.transactionId = :transactionId")
    fun deleteAllTransaction(transactionId: String)

    @Query("SELECT * FROM `inbound` WHERE `inbound`.transactionId = :transactionId ORDER BY packageNumber DESC LIMIT 1")
    fun getByLastInbound(transactionId: String): InboundEntity?

    @Query("SELECT COUNT(*) FROM `inbound` WHERE `inbound`.transactionId = :transactionId")
    fun countByTransaction(transactionId: String): Long
}