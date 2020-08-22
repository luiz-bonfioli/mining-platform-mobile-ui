package br.com.mining.mobile.shared.repository

import br.com.mining.mobile.shared.model.BaseEntity

interface RepositoryBase<T : BaseEntity> {

    fun insert(entity: T): Long

    fun update(entity: T)

    fun insertAll(entities: List<T>)

    fun upsert(entities: List<T>)

    fun delete(entity: T)
}