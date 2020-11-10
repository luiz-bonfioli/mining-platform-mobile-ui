package br.com.mining.mobile.shared.repository

import br.com.mining.mobile.shared.enums.MachineStatus
import br.com.mining.mobile.shared.model.Inbound

interface InboundRepository : RepositoryBase<Inbound> {

    fun findByTransaction(transactionId: String, stauts: MachineStatus): Inbound?

    fun findByTransaction(transactionId: String, numberOfPackage: Int): Inbound?

    fun findByLastInbound(transactionId: String): Inbound?

    fun findByTransaction(transactionId: String): List<Inbound>?

    fun countByTransaction(transactionId: String): Long
}