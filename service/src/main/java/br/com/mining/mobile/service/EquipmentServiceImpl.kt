package br.com.mining.mobile.service

import android.annotation.SuppressLint
import android.util.Log
import br.com.mining.mobile.service.messaging.MqttChannel
import br.com.mining.mobile.shared.enums.EquipmentServiceState
import br.com.mining.mobile.shared.model.Equipment
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.mobile.shared.repository.EquipmentRepository
import br.com.mining.mobile.shared.service.EquipmentService
import com.mining.platform.equipment.EquipmentPackageOuterClass.EquipmentListPackage
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.nio.ByteBuffer
import java.util.*

@SuppressLint("CheckResult")
object EquipmentServiceImpl : EquipmentService, KoinComponent {

    private const val TAG = "EquipmentService"
    private const val EQUIPMENT_BYTE = 2
    private const val DEFAULT_RESULT_OFFSET = 50

    private val repository: EquipmentRepository by inject()
    private val mqttChannel: MqttChannel by inject()

    private var observable: PublishSubject<EquipmentServiceState> = PublishSubject.create()

    init {
        observable.subscribeOn(Schedulers.newThread())
        observable.observeOn(AndroidSchedulers.mainThread())
    }

    override fun addSubscriber(subscriber: Consumer<EquipmentServiceState>): Disposable {
        return observable.subscribe(subscriber)
    }

    override fun createEquipment(result: () -> Unit) {
        Single.fromCallable {
            for (i in 0..10) {
                val equipment: Equipment by inject()
                equipment.deviceId = UUID.randomUUID().toString()
                equipment.name = "Equipamento 0$i"
                equipment.shortName = "Equipamento 0$i"
                equipment.id = UUID.randomUUID().toString()
                repository.insert(equipment)
            }
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                result()
            }, { e ->
                e.printStackTrace()
            })
    }


    override fun getEquipment(page: Int, callback: (equipments: List<Equipment>) -> Unit) {
        Single.fromCallable {
            repository.getAll(DEFAULT_RESULT_OFFSET, page)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                callback(it)
            }, { e ->
                e.printStackTrace()
                callback(mutableListOf())
            })
    }

    override fun importEquipment() {
        mqttChannel.subscribe(Protocol.Topic.DIAGNOSTIC + Protocol.Topic.RESPONSE)
        val payload = ByteBuffer.allocate(EQUIPMENT_BYTE)
        payload.put(Protocol.Service.EQUIPMENT)
        payload.put(Protocol.Event.EQUIPMENT_LIST)
        mqttChannel.publish(payload.array(), Protocol.Topic.DIAGNOSTIC + Protocol.Topic.REQUEST)
    }

    override fun onMessageArrived(message: ByteArray, eventId: Byte, topic: String) {
        Log.d("EquipmentService", "********** Chegou **********")
        when (eventId) {
            Protocol.Event.EQUIPMENT_LIST -> parseEquipment(message)
        }
    }

    private fun parseEquipment(message: ByteArray) {
        Single.fromCallable {
            val equipmentListPackage = EquipmentListPackage.parseFrom(message)
            equipmentListPackage.equipmentsList.forEach {
                val equipment: Equipment by inject()
                equipment.deviceId = it.deviceId
                equipment.name = it.name
                equipment.shortName = it.shortName
                equipment.id = it.id
                repository.insert(equipment)
            }
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                observable.onNext(EquipmentServiceState.UPDATE_DATA)
            }, { e ->
                e.printStackTrace()
                observable.onNext(EquipmentServiceState.UPDATE_ERRO)
            })
    }


}