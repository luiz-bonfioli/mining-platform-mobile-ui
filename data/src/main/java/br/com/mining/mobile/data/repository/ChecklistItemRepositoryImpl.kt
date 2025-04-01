package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.ChecklistItemDao
import br.com.mining.mobile.data.model.ChecklistItemEntity
import br.com.mining.mobile.shared.model.ChecklistItem
import br.com.mining.mobile.shared.repository.ChecklistItemRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChecklistItemRepositoryImpl :
    RepositoryBaseImpl<ChecklistItemDao, ChecklistItemEntity, ChecklistItem>(),
    ChecklistItemRepository, KoinComponent {

    override val dao: ChecklistItemDao by inject()

    override fun getById(id: String): ChecklistItem {
        TODO("Not yet implemented")
    }
}