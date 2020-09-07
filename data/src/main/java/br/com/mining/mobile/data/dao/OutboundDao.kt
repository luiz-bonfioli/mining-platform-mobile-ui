package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.OutboundEntity

@Dao
interface OutboundDao : DaoBase<OutboundEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}