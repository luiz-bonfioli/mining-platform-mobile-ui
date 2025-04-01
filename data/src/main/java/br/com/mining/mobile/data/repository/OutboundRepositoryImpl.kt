package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.OutboundDao
import br.com.mining.mobile.data.model.OutboundEntity
import br.com.mining.mobile.shared.model.Outbound
import br.com.mining.mobile.shared.repository.OutboundRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class OutboundRepositoryImpl : RepositoryBaseImpl<OutboundDao, OutboundEntity, Outbound>(),
    OutboundRepository, KoinComponent {

    override val dao: OutboundDao by inject()

    override fun getById(id: String): Outbound {
        TODO("Not yet implemented")
    }


}