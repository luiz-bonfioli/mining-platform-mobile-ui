package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.OrganizationDao
import br.com.mining.mobile.data.model.OrganizationEntity
import br.com.mining.mobile.shared.model.Organization
import br.com.mining.mobile.shared.repository.OrganizationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class OrganizationRepositoryImpl : RepositoryBaseImpl<OrganizationDao, OrganizationEntity, Organization>(),
    OrganizationRepository, KoinComponent {

    override val dao: OrganizationDao by inject()

    override fun getById(id: String): Organization {
        TODO("Not yet implemented")
    }
}