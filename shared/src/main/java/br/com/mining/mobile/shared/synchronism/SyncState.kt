package br.com.mining.mobile.shared.synchronism

import br.com.mining.mobile.shared.enums.TransactionType
import br.com.mining.mobile.shared.synchronism.enums.SyncStatus

data class SyncState(
    var id: String,
    var state: SyncStatus = SyncStatus.FINISH,
    var topic: String = "",
    var type: TransactionType = TransactionType.IMPORT,
    var packageNumber: Int = 0,
    var numberOfPackage: Int = 0
) {
    fun isValid() = numberOfPackage != 0

    fun isImport() = type == TransactionType.IMPORT


}