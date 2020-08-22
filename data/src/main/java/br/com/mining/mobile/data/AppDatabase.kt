package br.com.mining.mobile.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.mining.mobile.data.dao.UserDao
import br.com.mining.mobile.data.model.UserEntity

@Database(
    entities = [
        UserEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}