package br.com.mining.mobile.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.mining.mobile.data.model.ChecklistEntity
import br.com.mining.mobile.data.model.EquipmentEntity

@Dao
interface ChecklistDao : DaoBase<ChecklistEntity>  {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }

    @Query("SELECT * FROM checklistentity LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): List<ChecklistEntity>
}