package br.com.mining.mobile.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.mining.mobile.data.model.UserEntity

@Dao
interface UserDao : DaoBase<UserEntity>  {

    companion object {
        const val DEFAULT_RESULT_OFFSET = 50
    }

    @Query("SELECT * FROM userentity LIMIT :offset OFFSET :limit")
    fun findAll(limit: Int, offset: Int = DEFAULT_RESULT_OFFSET): List<UserEntity>


    @Query("SELECT * FROM userentity")
    fun getAll(): List<UserEntity>

}