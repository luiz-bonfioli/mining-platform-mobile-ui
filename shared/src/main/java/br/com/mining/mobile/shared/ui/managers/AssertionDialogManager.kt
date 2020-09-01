package br.com.mining.mobile.shared.ui.managers

import br.com.mining.mobile.shared.ui.listeners.OnDialogAssertionListener
import com.afollestad.materialdialogs.MaterialDialog

abstract class AssertionDialogManager : OnDialogAssertionListener {

    protected var dialog: MaterialDialog? = null

    private var successCallback: (MaterialDialog?) -> Unit = { }
    private var errorCallback: (MaterialDialog?) -> Unit = { }

    private var dismissOnError: Boolean = false
    private var dismissOnSuccess: Boolean = false

    abstract fun open()

    fun success(autoDismiss: Boolean = true, callback: (MaterialDialog?) -> Unit) {
        successCallback = callback
        dismissOnSuccess = autoDismiss
    }

    fun error(autoDismiss: Boolean = true, callback: (MaterialDialog?) -> Unit) {
        errorCallback = callback
        dismissOnError = autoDismiss
    }

    inline fun show(func: AssertionDialogManager.() -> Unit): AssertionDialogManager {
        this.func()
        this.open()
        return this
    }

    //region ~~~~ OnAdminAuthListener Implementation ~~~~
    override fun onClose() {
        dialog?.dismiss()
    }

    override fun onSucceed() {
        if (dismissOnSuccess) {
            onClose()
        }

        successCallback.invoke(dialog)
    }

    override fun onError() {
        if (dismissOnError) {
            onClose()
        }

        errorCallback.invoke(dialog)
    }
    //endregion

}
