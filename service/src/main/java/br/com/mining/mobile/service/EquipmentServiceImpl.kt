package br.com.mining.mobile.service

import android.util.Log
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.platform.shared.listeners.MessageListener
import com.mining.platform.equipment.EquipmentPackageOuterClass.EquipmentListPackage

class EquipmentServiceImpl : MessageListener {


    override fun onMessageArrived(message: ByteArray, eventId: Byte, topic: String) {
        Log.d("EquipmentService", "********** Chegou **********")
        when (eventId) {
            Protocol.Event.EQUIPMENT_LIST -> {
                val x = EquipmentListPackage.parseFrom(message)
                x.equipmentsList.forEach {
                    it.deviceId
                }
            }
        }
    }


}