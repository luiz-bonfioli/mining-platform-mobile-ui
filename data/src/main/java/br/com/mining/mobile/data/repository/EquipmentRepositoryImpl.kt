package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.EquipmentDao
import br.com.mining.mobile.data.model.EquipmentEntity
import br.com.mining.mobile.shared.model.Equipment
import br.com.mining.mobile.shared.model.User
import br.com.mining.mobile.shared.repository.EquipmentRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class EquipmentRepositoryImpl : RepositoryBaseImpl<EquipmentDao, EquipmentEntity, Equipment>(),
    EquipmentRepository, KoinComponent {

    override val dao: EquipmentDao by inject()

    override fun getById(id: String): Equipment {
        TODO("Not yet implemented")
    }

    override fun getAll(limit: Int, page: Int): List<Equipment> =
        dao.getAll(limit, limit * page)
}