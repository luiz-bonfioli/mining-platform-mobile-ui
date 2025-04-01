package br.com.mining.mobile.shared.service

interface MqttListener {
    fun onConnect()
    fun onDisconnect()
}