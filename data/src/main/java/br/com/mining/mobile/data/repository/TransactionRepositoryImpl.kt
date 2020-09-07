package br.com.mining.mobile.data.repository

import br.com.mining.mobile.data.dao.TransactionDao
import br.com.mining.mobile.data.model.TransactionEntity
import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.mobile.shared.repository.TransactionRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class TransactionRepositoryImpl : RepositoryBaseImpl<TransactionDao, TransactionEntity, Transaction>(),
    TransactionRepository, KoinComponent {

    override val dao: TransactionDao by inject()

    override fun getById(id: String): Transaction {
        TODO("Not yet implemented")
    }


}