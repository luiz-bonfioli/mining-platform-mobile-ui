package br.com.mining.mobile.viewmodels.items

import br.com.mining.mobile.shared.model.Equipment

class EquipmentItemViewModel(
    var id: String = "",
    var name: String = ""
) {

    companion object {
        fun bind(equipment: Equipment): EquipmentItemViewModel =
            EquipmentItemViewModel(equipment.id, equipment.name)
    }
}