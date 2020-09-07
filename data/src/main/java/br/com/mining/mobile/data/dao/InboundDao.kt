package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.InboundEntity

@Dao
interface InboundDao : DaoBase<InboundEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}