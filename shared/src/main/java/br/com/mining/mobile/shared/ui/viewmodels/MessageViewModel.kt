package br.com.mining.mobile.shared.ui.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent

class MessageViewModel : ViewModel(), KoinComponent {

    val title = ObservableField<String>()
    val description = ObservableField<String>()

}