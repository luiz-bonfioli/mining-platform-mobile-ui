package br.com.mining.mobile.data.di

import androidx.room.Room
import br.com.mining.mobile.data.AppDatabase
import br.com.mining.mobile.data.model.UserEntity
import br.com.mining.mobile.data.repository.UserRepositoryImpl
import br.com.mining.mobile.shared.model.User
import br.com.mining.mobile.shared.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "mining").build()
    }

    single { get<AppDatabase>().userDao() }
    single<User> { UserEntity() }
    single<UserRepository> { UserRepositoryImpl() }


}


