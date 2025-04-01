package br.com.mining.mobile.viewmodels.items

import br.com.mining.mobile.shared.model.Checklist

class ChecklistItemViewModel(
                             var id: String = "",
                             var name: String = ""
) {
    companion object {
        fun bind(checklist: Checklist): ChecklistItemViewModel =
            ChecklistItemViewModel(checklist.id, checklist.name)
    }
}