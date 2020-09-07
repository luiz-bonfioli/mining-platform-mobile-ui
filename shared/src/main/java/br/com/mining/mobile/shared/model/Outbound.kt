package br.com.mining.mobile.shared.model

import br.com.mining.mobile.shared.enums.MachineStatus

interface Outbound : BaseEntity {
    var status: MachineStatus
    var transactionId: String
    var numberOfPackage: Int
    var content: ByteArray
}