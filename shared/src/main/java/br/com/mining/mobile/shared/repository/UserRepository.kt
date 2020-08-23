package br.com.mining.mobile.shared.repository

import br.com.mining.mobile.shared.model.User

interface UserRepository : RepositoryBase<User> {
    fun getAll(): List<User>?
}