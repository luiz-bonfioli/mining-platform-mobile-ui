package br.com.mining.mobile.service.di

import br.com.mining.mobile.data.di.dataModule
import br.com.mining.mobile.service.CompanyServiceImpl
import br.com.mining.mobile.service.DeviceServiceImpl
import br.com.mining.mobile.service.EquipmentServiceImpl
import br.com.mining.mobile.service.ImportServiceImpl
import br.com.mining.mobile.service.messaging.MqttChannel
import br.com.mining.mobile.service.messaging.MqttChannelImpl
import br.com.mining.mobile.shared.protocol.Protocol
import br.com.mining.mobile.shared.service.ImportService
import br.com.mining.platform.shared.listeners.MessageListener
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.inject
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appDataModule = dataModule

val serviceModule = module {

    single<ImportService> { ImportServiceImpl() }

    single<MqttChannel> { MqttChannelImpl }

    single<MessageListener>(named(Protocol.Service.COMPANY.toString())) { CompanyServiceImpl() }

    single<MessageListener>(named(Protocol.Service.DEVICE.toString())) { DeviceServiceImpl() }

    single<MessageListener>(named(Protocol.Service.EQUIPMENT.toString())) { EquipmentServiceImpl() }

}

