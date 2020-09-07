package br.com.mining.mobile.shared.model

import br.com.mining.mobile.shared.enums.MachineStatus

interface Inbound : BaseEntity {
    var status: MachineStatus
    var transactionId: String
    var packageNumber: Int
    var content: ByteArray
}