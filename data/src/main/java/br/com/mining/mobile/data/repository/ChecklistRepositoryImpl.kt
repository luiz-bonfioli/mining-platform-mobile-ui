package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.CheckListDao
import br.com.mining.mobile.data.model.ChecklistEntity
import br.com.mining.mobile.shared.model.Checklist
import br.com.mining.mobile.shared.repository.ChecklistRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChecklistRepositoryImpl : RepositoryBaseImpl<CheckListDao, ChecklistEntity, Checklist>(),
    ChecklistRepository, KoinComponent {

    override val dao: CheckListDao by inject()

    override fun getById(id: String): Checklist {
        TODO("Not yet implemented")
    }

}