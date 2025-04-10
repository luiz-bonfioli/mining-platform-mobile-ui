package br.com.mining.mobile.shared.repository

import br.com.mining.mobile.shared.model.Checklist

interface ChecklistRepository : RepositoryBase<Checklist> {

    fun getAll(limit: Int, page: Int): List<Checklist>
}