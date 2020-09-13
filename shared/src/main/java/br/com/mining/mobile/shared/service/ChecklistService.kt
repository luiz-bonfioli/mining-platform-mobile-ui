package br.com.mining.mobile.shared.service

import br.com.mining.mobile.shared.model.Checklist

interface ChecklistService {

    fun createChecklist(result: () -> Unit)

    fun getChecklist(page: Int, callback: (checklists: List<Checklist>) -> Unit)
}