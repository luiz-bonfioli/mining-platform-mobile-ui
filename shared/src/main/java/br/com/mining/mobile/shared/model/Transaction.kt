package br.com.mining.mobile.shared.model

import br.com.mining.mobile.shared.enums.TransactionState
import br.com.mining.mobile.shared.enums.TransactionType
import java.util.*

interface Transaction : BaseEntity {
    var type: TransactionType
    var topic: String
    var status: TransactionState
    var module: Byte?
    var event: Byte?
    var packageNumber: Int
    var createdAt: Long
    fun fecthTransactionId(): UUID
    fun isValid(): Boolean
}