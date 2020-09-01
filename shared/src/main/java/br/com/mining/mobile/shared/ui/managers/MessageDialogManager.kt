package br.com.mining.mobile.shared.ui.managers

import android.content.Context
import br.com.mining.mobile.shared.ui.listeners.OnMessageDialogListener
import br.com.mining.mobile.shared.ui.view.MessageView
import com.afollestad.materialdialogs.MaterialDialog

class MessageDialogManager(
    private var title: String = "",
    private var description: String = "",
    private val context: Context
) : OnMessageDialogListener {

    private var dialog: MaterialDialog? = null

    private var successCallback: () -> Unit = { }
    private var closeCallback: () -> Unit = { }

    fun open() {
        dialog = MessageView.openAtDialog(title, description, context, this)
    }

    fun success(callback: () -> Unit) {
        successCallback = callback
    }

    fun close(callback: () -> Unit) {
        closeCallback = callback
    }

    inline fun show(func: MessageDialogManager.() -> Unit): MessageDialogManager {
        this.func()
        this.open()
        return this
    }

    //region ~~~~ OnMessageDialogListener ~~~~
    override fun onClose() {
        dialog?.dismiss()
        closeCallback.invoke()
    }

    override fun onSucceed() {
        dialog?.dismiss()
        successCallback.invoke()
    }
    //endregion
}