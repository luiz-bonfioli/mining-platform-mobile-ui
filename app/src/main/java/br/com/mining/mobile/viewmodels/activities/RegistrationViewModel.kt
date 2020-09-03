package br.com.mining.mobile.viewmodels.activities

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {

    val currencyText = ObservableField("")

    fun addSmaller() {
        currencyText.set(currencyText.get() + "-")
    }

    fun backspace() {
        currencyText.set("")
    }

    fun update(value: Int = 0, removeLast: Boolean = false) {
        currencyText.set(currencyText.get() + value.toString())
    }
}