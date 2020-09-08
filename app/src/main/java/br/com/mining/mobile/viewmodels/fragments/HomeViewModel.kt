package br.com.mining.mobile.viewmodels.fragments

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.service.MqttConncetService
import br.com.mining.mobile.shared.service.ImportService
import br.com.mining.mobile.shared.service.MqttListener
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class HomeViewModel : ViewModel(), KoinComponent {

    private val service: ImportService by inject()

    fun test() {
        service.importEquipment()
    }

    fun connect(context: Context) {
        MqttConncetService.connect(context, UUID.randomUUID().toString(), "143.110.140.7", "1883",
            "mining-dev:mining-dev", "miningplatform", object : MqttListener {
                override fun onConnect() {
                    Log.d("TAGGG", "******** onConnect ********")
                }

                override fun onDisconnect() {
                    Log.d("TAGGG", "******** onDisconnect ********")
                }

            })
    }

    //region ~~~~ Factory ~~~~
    class Factory(
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = HomeViewModel() as T
    }
    //endregion
}