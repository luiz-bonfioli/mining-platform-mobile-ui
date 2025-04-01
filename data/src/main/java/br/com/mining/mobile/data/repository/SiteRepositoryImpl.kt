package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.SiteDao
import br.com.mining.mobile.data.model.SiteEntity
import br.com.mining.mobile.shared.model.Site
import br.com.mining.mobile.shared.repository.SiteRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class SiteRepositoryImpl : RepositoryBaseImpl<SiteDao, SiteEntity, Site>(), SiteRepository,
    KoinComponent {

    override val dao: SiteDao by inject()

    override fun getById(id: String): Site {
        TODO("Not yet implemented")
    }


}