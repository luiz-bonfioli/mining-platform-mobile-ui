package br.com.mining.mobile.service.messaging

import br.com.mining.platform.shared.MqttStatus
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface MqttChannel {
    fun connect(clientId: String, host: String, port: String, userName: String, password: String)

    fun publish(message: ByteArray, topic: String)

    fun subscribe(topic: String)

    fun unsubscribe(topic: String)

    fun reconnect()

    fun close()

    fun addSubscriber(subscriber: Consumer<MqttStatus>): Disposable

    fun getStatus(): MqttStatus
}