package br.com.mining.mobile.data.repository


import br.com.mining.mobile.data.dao.DaoBase
import br.com.mining.mobile.shared.model.BaseEntity
import br.com.mining.mobile.shared.repository.RepositoryBase
import org.koin.core.KoinComponent

abstract class RepositoryBaseImpl<DAO : DaoBase<E>, E, IE : BaseEntity> : RepositoryBase<IE>,
    KoinComponent {

    abstract val dao: DAO

    abstract fun getById(id: String): IE

    override fun insert(entity: IE): Long {
        return dao.insert(entity as E)
    }

    override fun update(entity: IE) {
        dao.update(entity as E)
    }

    override fun delete(entity: IE) {
        TODO("Not yet implemented")
    }

    override fun upsert(entities: List<IE>) {
        TODO("Not yet implemented")
    }

    override fun insertAll(entities: List<IE>) {
        for (entity in entities) {
            dao.insert(entity as E)
        }
    }

}



