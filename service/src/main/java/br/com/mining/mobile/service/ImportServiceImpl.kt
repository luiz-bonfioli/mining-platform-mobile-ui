package br.com.mining.mobile.service

import br.com.mining.mobile.service.messaging.MqttChannel
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.mobile.shared.service.ImportService
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.nio.ByteBuffer

class ImportServiceImpl : ImportService,  KoinComponent {
    private val mqttChannel: MqttChannel by inject()

    override fun importEquipment() {
        mqttChannel.subscribe(Protocol.Topic.DIAGNOSTIC + Protocol.Topic.RESPONSE)
        val payload = ByteBuffer.allocate(2)
        payload.put(Protocol.Service.EQUIPMENT)
        payload.put(Protocol.Event.EQUIPMENT_LIST)
        mqttChannel.publish(payload.array(), Protocol.Topic.DIAGNOSTIC + Protocol.Topic.REQUEST)
    }
}