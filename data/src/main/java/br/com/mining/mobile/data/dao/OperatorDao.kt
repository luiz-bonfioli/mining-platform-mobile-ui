package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.OperatorEntity

@Dao
interface OperatorDao : DaoBase<OperatorEntity>  {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}