package br.com.mining.mobile.viewmodels.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.adapters.ChecklistAdapter
import br.com.mining.mobile.shared.service.ChecklistService
import br.com.mining.mobile.shared.service.EquipmentService
import br.com.mining.mobile.viewmodels.items.ChecklistItemViewModel
import br.com.mining.mobile.viewmodels.items.EquipmentItemViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ChecklistViewModel(
    private val adapter: ChecklistAdapter
) : ViewModel(), KoinComponent {

    private val checklistService: ChecklistService by inject()

    fun starList() {
//        checklistService.createChecklist {
            checklistService.getChecklist(0) { itens ->
                adapter.update(itens.map { ChecklistItemViewModel.bind(it) })
            }
//        }
    }

    //region ~~~~ Factory ~~~~
    class Factory(
        private val adapter: ChecklistAdapter
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            ChecklistViewModel(adapter) as T
    }
    //endregion
}