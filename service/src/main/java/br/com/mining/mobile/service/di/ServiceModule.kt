package br.com.mining.mobile.service.di

import br.com.mining.mobile.data.di.dataModule
import br.com.mining.mobile.service.*
import br.com.mining.mobile.service.messaging.MqttChannel
import br.com.mining.mobile.service.messaging.MqttChannelImpl
import br.com.mining.mobile.service.synchronism.SynchronismImpl
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.mobile.shared.service.*
import br.com.mining.mobile.shared.synchronism.Synchronism
import br.com.mining.platform.shared.listeners.MessageListener
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.inject
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDataModule = dataModule

val serviceModule = module {

    single<Synchronism> { SynchronismImpl() }
    single<ImportService> { ImportServiceImpl() }
    single<ChecklistService> { ChecklistServiceImpl }

    single<MqttChannel> { MqttChannelImpl }

    single<CompanyService>(named(Protocol.Service.COMPANY.toString())) { CompanyServiceImpl }
    single<CompanyService> { CompanyServiceImpl }

    single<DeviceService>(named(Protocol.Service.DEVICE.toString())) { DeviceServiceImpl }
    single<DeviceService> { DeviceServiceImpl }

    single<EquipmentService>(named(Protocol.Service.EQUIPMENT.toString())) { EquipmentServiceImpl }
    single<EquipmentService> { EquipmentServiceImpl }

}

