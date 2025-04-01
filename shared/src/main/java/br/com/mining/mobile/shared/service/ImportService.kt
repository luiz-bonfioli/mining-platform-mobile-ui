package br.com.mining.mobile.shared.service

import br.com.mining.mobile.shared.model.Transaction
import br.com.mining.mobile.shared.synchronism.SyncModelState
import br.com.mining.mobile.shared.synchronism.SyncState
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface ImportService {

    fun addSubscriber(subscriber: Consumer<SyncState>): Disposable

    fun importData(topic: String, payload: ByteArray, mostCurrent: Boolean, subscriber: Consumer<SyncModelState>)

    fun resume()

    fun pause()

    fun abort(topic: String)

    fun onProcess(message: ByteArray, topic: String)

    fun startTransaction(transaction: Transaction)

    fun importAttachment(topic: String, payload: ByteArray, subscriber: Consumer<SyncModelState>)

    fun resumeAttachment(topic: String, transaction: Transaction, subscriber: Consumer<SyncModelState>)

    fun register(topic: String, subscriber: Consumer<SyncModelState>)

}