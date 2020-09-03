package br.com.mining.mobile.shared.ui.managers

import android.content.Context
import br.com.mining.mobile.shared.ui.view.LoadingView
import com.afollestad.materialdialogs.MaterialDialog

class CustomDialogManager(
    private val context: Context
) {

    private var currentDialog: MaterialDialog? = null

    fun showLoading(title: String) {
        currentDialog?.dismiss()
        currentDialog = LoadingView.openAtDialog(context, title)
    }

    fun dismiss() {
        if (currentDialog?.isShowing == true) {
            currentDialog?.dismiss()
        }
    }

}
