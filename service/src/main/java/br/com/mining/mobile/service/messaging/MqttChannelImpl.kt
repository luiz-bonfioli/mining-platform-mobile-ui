package br.com.mining.mobile.service.messaging

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.text.TextUtils
import android.util.Log
import br.com.mining.platform.service.messaging.CommunicationService
import br.com.mining.platform.service.messaging.MqttConstants
import br.com.mining.platform.shared.MqttStatus
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.nio.ByteBuffer
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class MqttChannelImpl(context: Context) : MqttChannel {

    companion object {
        private const val TAG = "MQTT"
        private const val TIMEOUT_SERVICE_CONNECTED = 1000
        private const val TIMEOUT_CONNECTED = 30000
    }

    init {
        context.bindService(
            Intent(context, CommunicationService::class.java),
            object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, service: IBinder) {
                    messenger = Messenger(service)
                    isServiceConnected = true
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    isServiceConnected = false
                }
            }, Context.BIND_AUTO_CREATE
        )
        initObservable()
    }

    private var messenger: Messenger? = null
    private val incomingMessenger: Messenger by lazy { Messenger(IncomingHandler()) }
    private var isServiceConnected = false
    private var status: MqttStatus = MqttStatus.INIT
    private val observable: PublishSubject<MqttStatus> by lazy {
        PublishSubject.create<MqttStatus>()
    }

    override fun addSubscriber(subscriber: Consumer<MqttStatus>): Disposable {
        return observable.subscribe(subscriber)
    }

    override fun getStatus(): MqttStatus = status

    override fun connect(clientId: String, host: String, port: String, userName: String,
                         password: String) {

        val msgBundle = Message.obtain(null, MqttConstants.CONNECT)
        val bundle = Bundle()
        bundle.putCharArray(MqttConstants.HOST, host.toCharArray())
        bundle.putCharArray(MqttConstants.PORT, port.toCharArray())
        bundle.putCharArray(MqttConstants.USER, userName.toCharArray())
        bundle.putCharArray(MqttConstants.PASS, password.toCharArray())
        bundle.putCharArray(MqttConstants.CLIENTID, clientId.toCharArray())
        msgBundle.data = bundle

        //set incoming callback
        msgBundle.replyTo = incomingMessenger
        Single.fromCallable {
            while (true) {
                if (isServiceConnected) {
                    messenger?.send(msgBundle)
                    break
                } else {
                    Thread.sleep(TIMEOUT_SERVICE_CONNECTED.toLong())
                }
            }
        }.timeout(TIMEOUT_CONNECTED.toLong(), TimeUnit.MILLISECONDS)
            .subscribe({
                updateStatus(MqttStatus.CONECTING)
            }, { error: Throwable ->
                error.printStackTrace()
                updateStatus(MqttStatus.ERROR)
            })
    }

    override fun reconnect() {
        if (status === MqttStatus.DISCONNECTED || status === MqttStatus.ERROR) {
            val msgBundle = Message.obtain(null, MqttConstants.RECONNECT)
            msgBundle.data = Bundle()
            messenger?.send(msgBundle)
        }
    }

    override fun close() {
        val msgBundle = Message.obtain(null, MqttConstants.CLOSE)
        msgBundle.data = Bundle()
        messenger?.send(msgBundle)
    }

    override fun publish(message: ByteArray, topic: String) {
        Log.d(TAG, "publish: $topic")
        try {
            val msgBundle = Message.obtain(null, MqttConstants.PUBLISH)
            val bundle = Bundle()
            bundle.putCharArray(MqttConstants.TOPIC, topic.toCharArray())
            bundle.putByteArray(MqttConstants.PAYLOAD, message)
            msgBundle.data = bundle
            messenger?.send(msgBundle)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun subscribe(topic: String) {
        Log.d(TAG, "subscribe: $topic")
        try {
            if (messenger != null) {
                val msgBundle = Message.obtain(null, MqttConstants.SUBSCRIBE)
                val bundle = Bundle()
                bundle.putCharArray(MqttConstants.TOPIC, topic.toCharArray())
                msgBundle.data = bundle
                messenger?.send(msgBundle)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun unsubscribe(topic: String) {
        Log.d(TAG, "unsubscribe: $topic")
        try {
            if (messenger != null && !TextUtils.isEmpty(topic)) {
                val msgBundle =
                    Message.obtain(null, MqttConstants.UNSUBSCRIBE)
                val bundle = Bundle()
                bundle.putCharArray(MqttConstants.TOPIC, topic.toCharArray())
                msgBundle.data = bundle
                messenger?.send(msgBundle)
            }
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private fun initObservable() {
        observable.subscribeOn(Schedulers.io())
        observable.subscribeOn(Schedulers.newThread())
        observable.observeOn(AndroidSchedulers.mainThread())
    }

    private fun updateStatus(status: MqttStatus) {
        this.status = status
        observable.onNext(status)
    }

    @SuppressLint("HandlerLeak")
    private inner class IncomingHandler : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MqttConstants.MESSAGE_ARRIVED -> {
                    Log.d(TAG, "MqttConstants.MESSAGE_ARRIVED")
                    try {
                        val bundle = msg.data
                        val topic = bundle.getCharArray(MqttConstants.TOPIC)
                        val payload = bundle.getByteArray(MqttConstants.PAYLOAD)
                        val payloadBuffer = ByteBuffer.wrap(payload)
                        val moduleId = payloadBuffer.get()
                        val eventId = payloadBuffer.get()
                        val content = ByteArray(payloadBuffer.remaining())
                        payloadBuffer[content]
//                        val messageListener: Any = Injector.get(moduleId)
//                        if (messageListener != null) {
//                            (messageListener as MessageListener).onMessageArrived(
//                                content, eventId,
//                                String(topic!!)
//                            )
//                        } else {
//                            Log.d(TAG, " ******* Injetor: $moduleId")
//                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG, e.message!!)
                    }
                }
                MqttConstants.MESSAGE_STATE -> {
                    Log.d(TAG, "MqttConstants.MESSAGE_STATE")
                    val bundle = msg.data
                    try {
                        val state = bundle.getString(MqttConstants.STATE) ?: ""
                        updateStatus(MqttStatus.valueOf(state))
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }
            }
        }
    }

}
