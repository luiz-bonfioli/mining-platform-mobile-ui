package br.com.mining.mobile.data.dao

import androidx.room.*

interface DaoBase<E> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: E): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: E)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(obj: List<E>): List<Long>

    @Update
    fun update(obj: List<E>)


}