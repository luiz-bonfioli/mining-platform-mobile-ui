package br.com.mining.mobile.service

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import br.com.mining.mobile.service.messaging.MqttChannel
import br.com.mining.mobile.shared.service.MqttListener
import br.com.mining.platform.shared.MqttStatus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

@SuppressLint("CheckResult")
object MqttConncetService : KoinComponent, Consumer<MqttStatus> {

//    fun xx(data: ByteArray) {
//        val x = EquipmentListPackage.parseFrom(data)
//        x.equipmentsList.forEach {
//            it.deviceId
//        }
//    }

    private val mqttChannel: MqttChannel by inject()

    private var listener: MqttListener? = null
    private val callback = mqttChannel.addSubscriber(this)

    fun connect(context: Context, clientId: String, host: String, port: String, userName: String, password: String,
                listener: MqttListener) {

        mqttChannel.start(context)
        this.listener = listener
        Single.fromCallable {
            if (mqttChannel.getStatus() == MqttStatus.CONECTED) {
                listener.onConnect()
            } else {
                mqttChannel.connect(clientId, host, port, userName, password)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
                Log.e("MqttConncet","Error: connect", it)
            })
    }

    fun close() {
        mqttChannel.close()
    }

    override fun accept(status: MqttStatus) {
        when (status) {
            MqttStatus.CONECTED -> listener?.onConnect()
            MqttStatus.DISCONNECTED, MqttStatus.ERROR -> listener?.onDisconnect()
        }
    }

}