package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.InboundDao
import br.com.mining.mobile.data.model.InboundEntity
import br.com.mining.mobile.shared.enums.MachineStatus
import br.com.mining.mobile.shared.model.Inbound
import br.com.mining.mobile.shared.repository.InboundRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class InboundRepositoryImpl : RepositoryBaseImpl<InboundDao, InboundEntity, Inbound>(),
    InboundRepository, KoinComponent {

    override val dao: InboundDao by inject()

    override fun getById(id: String): Inbound? = dao.getById(id)

    override fun findByTransaction(transactionId: String, stauts: MachineStatus): Inbound? =
        dao.getByTransaction(transactionId, stauts)

    override fun findByTransaction(transactionId: String, numberOfPackage: Int): Inbound? =
        dao.getByTransaction(transactionId, numberOfPackage)

    override fun findByTransaction(transactionId: String): List<Inbound>? = dao.getByTransaction(transactionId)

    override fun findByLastInbound(transactionId: String): Inbound? = dao.getByLastInbound(transactionId)

    override fun countByTransaction(transactionId: String): Long = dao.countByTransaction(transactionId)

}