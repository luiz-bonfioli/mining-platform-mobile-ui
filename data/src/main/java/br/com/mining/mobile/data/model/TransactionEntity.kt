package br.com.mining.mobile.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mining.mobile.shared.enums.TransactionState
import br.com.mining.mobile.shared.enums.TransactionType
import br.com.mining.mobile.shared.model.Transaction
import java.util.*

@Entity
data class TransactionEntity(
    @PrimaryKey
    override var id: String = "",
    override var topic: String = "",
    override var status: TransactionState = TransactionState.FINISH,
    override var persist: Boolean = true,
    override var type: TransactionType = TransactionType.IMPORT,
    override var module: Byte? = null,
    override var event: Byte? = null,
    override var packageNumber: Int = 0,
    override var createdAt: Long = 0L
) : Transaction {

    override fun fecthTransactionId(): UUID = UUID.fromString(id)

    override fun isValid(): Boolean =
        !mutableListOf(TransactionState.PAUSE, TransactionState.CANCEL).contains(status)

}