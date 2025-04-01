package br.com.mining.mobile.data.dao

import androidx.room.Dao
import br.com.mining.mobile.data.model.OrganizationEntity

@Dao
interface OrganizationDao : DaoBase<OrganizationEntity> {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }
}