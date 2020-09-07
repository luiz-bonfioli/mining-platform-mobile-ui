package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.InboundDao
import br.com.mining.mobile.data.model.InboundEntity
import br.com.mining.mobile.shared.model.Inbound
import br.com.mining.mobile.shared.repository.InboundRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class InboundRepositoryImpl : RepositoryBaseImpl<InboundDao, InboundEntity, Inbound>(),
    InboundRepository, KoinComponent {

    override val dao: InboundDao by inject()

    override fun getById(id: String): Inbound {
        TODO("Not yet implemented")
    }


}