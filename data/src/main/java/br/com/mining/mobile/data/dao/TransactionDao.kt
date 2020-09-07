package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.TransactionEntity

@Dao
interface TransactionDao : DaoBase<TransactionEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}