package br.com.mining.mobile.shared.synchronism

import br.com.mining.mobile.shared.synchronism.enums.SyncModelStatus

data class SyncModelState(
        var modelStatus: SyncModelStatus
) {
    var topic: String = ""
    var transactionId: String = ""
    var packageNumber: Int = 0
    var numberOfPackage: Int = 0

    override fun toString(): String {
        return "SyncState( status=$modelStatus, topic='$topic', transactionId='$transactionId')"
    }

}