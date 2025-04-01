package br.com.mining.mobile.shared.service

import br.com.mining.mobile.shared.enums.EquipmentServiceState
import br.com.mining.mobile.shared.model.Equipment
import br.com.mining.platform.shared.listeners.MessageListener
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface EquipmentService : MessageListener {

    fun importEquipment()

    fun addSubscriber(subscriber: Consumer<EquipmentServiceState>): Disposable

    fun getEquipment(page: Int, callback: (equipments: List<Equipment>) -> Unit)

    fun createEquipment(result: () -> Unit)
}