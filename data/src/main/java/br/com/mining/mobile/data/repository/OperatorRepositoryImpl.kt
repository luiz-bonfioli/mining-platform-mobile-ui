package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.OperatorDao
import br.com.mining.mobile.data.dao.OrganizationDao
import br.com.mining.mobile.data.model.OperatorEntity
import br.com.mining.mobile.shared.model.Operator
import br.com.mining.mobile.shared.repository.OperatorRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class OperatorRepositoryImpl : RepositoryBaseImpl<OperatorDao, OperatorEntity, Operator>(),
    OperatorRepository, KoinComponent {

    override val dao: OperatorDao by inject()

    override fun getById(id: String): Operator {
        TODO("Not yet implemented")
    }


}