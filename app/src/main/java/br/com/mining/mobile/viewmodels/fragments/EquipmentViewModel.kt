package br.com.mining.mobile.viewmodels.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.adapters.EquipmentAdapter
import br.com.mining.mobile.shared.model.Equipment
import br.com.mining.mobile.shared.service.EquipmentService
import br.com.mining.mobile.viewmodels.items.EquipmentItemViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class EquipmentViewModel(
    private val adapter: EquipmentAdapter
) : ViewModel(), KoinComponent {

    private val equipmentService: EquipmentService by inject()

    fun starList() {
//        equipmentService.createEquipment() {
            equipmentService.getEquipment(0) { equipments ->
                adapter.update(equipments.map { EquipmentItemViewModel.bind(it) })
            }
//        }
    }

    //region ~~~~ Factory ~~~~
    class Factory(
        private val adapter: EquipmentAdapter
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            EquipmentViewModel(adapter) as T
    }
    //endregion
}