package br.com.mining.mobile.service.di

import br.com.mining.mobile.data.di.dataModule
import br.com.mining.mobile.service.messaging.MqttChannel
import br.com.mining.mobile.service.messaging.MqttChannelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appDataModule = dataModule

val serviceModule = module {
    single<MqttChannel> { MqttChannelImpl(androidContext()) }

}