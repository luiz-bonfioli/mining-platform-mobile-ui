package br.com.mining.mobile.service

import br.com.mining.mobile.shared.service.DeviceService
import br.com.mining.platform.shared.listeners.MessageListener

object DeviceServiceImpl : DeviceService {

    override fun onMessageArrived(message: ByteArray, eventId: Byte, topic: String) {
    }


}