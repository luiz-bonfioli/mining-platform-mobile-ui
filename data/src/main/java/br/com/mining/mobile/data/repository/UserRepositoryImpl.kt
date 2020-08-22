package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.UserDao
import br.com.mining.mobile.data.model.UserEntity
import br.com.mining.mobile.shared.model.User
import br.com.mining.mobile.shared.repository.UserRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserRepositoryImpl : RepositoryBaseImpl<UserDao, UserEntity, User>(), UserRepository,
    KoinComponent {

    override val dao: UserDao by inject()

    override fun getById(id: String): User {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<User> = dao.getAll()


}