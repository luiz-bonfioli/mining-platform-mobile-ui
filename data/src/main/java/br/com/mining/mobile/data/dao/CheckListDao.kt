package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.ChecklistEntity

@Dao
interface CheckListDao : DaoBase<ChecklistEntity>  {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}