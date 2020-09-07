package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.SiteEntity

@Dao
interface SiteDao : DaoBase<SiteEntity>  {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}