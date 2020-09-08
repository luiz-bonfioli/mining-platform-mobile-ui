package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.EquipmentEntity

@Dao
interface EquipmentDao : DaoBase<EquipmentEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}