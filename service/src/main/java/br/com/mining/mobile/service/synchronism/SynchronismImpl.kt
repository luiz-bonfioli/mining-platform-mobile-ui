package br.com.mining.mobile.service.synchronism

import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.mobile.shared.synchronism.RequestHeader
import br.com.mining.mobile.shared.synchronism.SyncModelState
import br.com.mining.mobile.shared.synchronism.SyncState
import br.com.mining.mobile.shared.synchronism.Synchronism
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

class SynchronismImpl : Synchronism {

    override fun addSubscriber(subscriber: Consumer<SyncState>): Disposable {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun reset() {
        TODO("Not yet implemented")
    }

    override fun <T : RequestHeader> importData(topic: String, payload: T, requireNewTransaction: Boolean,
        subscriber: Consumer<SyncModelState>) {
        TODO("Not yet implemented")
    }

    override fun register(topic: String, subscriber: Consumer<SyncModelState>) {
        TODO("Not yet implemented")
    }

    override fun exportData(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override fun onMessageArrived(message: ByteArray, eventId: Byte, topic: String) {
        TODO("Not yet implemented")
    }
}