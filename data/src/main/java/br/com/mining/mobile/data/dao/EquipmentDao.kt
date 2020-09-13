package br.com.mining.mobile.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.mining.mobile.data.model.EquipmentEntity
import br.com.mining.mobile.data.model.UserEntity

@Dao
interface EquipmentDao : DaoBase<EquipmentEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }

    @Query("SELECT * FROM equipmententity LIMIT :limit OFFSET :offset")
    fun getAll(limit: Int, offset: Int): List<EquipmentEntity>
}