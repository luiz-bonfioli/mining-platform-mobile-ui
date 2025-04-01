package br.com.mining.mobile.shared.repository

import br.com.mining.mobile.shared.model.Equipment

interface EquipmentRepository : RepositoryBase<Equipment> {

    fun getAll(limit: Int, page: Int): List<Equipment>
}