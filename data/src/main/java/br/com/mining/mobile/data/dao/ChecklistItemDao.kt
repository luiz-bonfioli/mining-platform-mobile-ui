package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.ChecklistItemEntity

@Dao
interface ChecklistItemDao : DaoBase<ChecklistItemEntity>  {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}