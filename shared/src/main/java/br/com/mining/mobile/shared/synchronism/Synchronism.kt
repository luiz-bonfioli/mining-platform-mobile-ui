package br.com.mining.mobile.shared.synchronism

import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.platform.shared.listeners.MessageListener
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface Synchronism : MessageListener {

    fun addSubscriber(subscriber: Consumer<SyncState>): Disposable

    fun pause()

    fun resume()

    fun reset()

    fun <T : RequestHeader> importData(topic: String, payload: T, requireNewTransaction: Boolean,
        subscriber: Consumer<SyncModelState>
    )

    fun register(topic: String, subscriber: Consumer<SyncModelState>)

    fun exportData(transaction: Transaction)
}