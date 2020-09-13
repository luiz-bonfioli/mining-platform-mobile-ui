package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.ChecklistDao
import br.com.mining.mobile.data.model.ChecklistEntity
import br.com.mining.mobile.shared.model.Checklist
import br.com.mining.mobile.shared.repository.ChecklistRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChecklistRepositoryImpl : RepositoryBaseImpl<ChecklistDao, ChecklistEntity, Checklist>(),
    ChecklistRepository, KoinComponent {

    override val dao: ChecklistDao by inject()

    override fun getById(id: String): Checklist {
        TODO("Not yet implemented")
    }

    override fun getAll(limit: Int, page: Int): List<Checklist> =
        dao.getAll(limit, limit * page)
}