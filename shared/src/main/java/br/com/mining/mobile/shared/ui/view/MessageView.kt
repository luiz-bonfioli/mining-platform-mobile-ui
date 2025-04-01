package br.com.mining.mobile.shared.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import br.com.mining.mobile.shared.R
import br.com.mining.mobile.shared.databinding.ViewMessageBinding
import br.com.mining.mobile.shared.ui.listeners.OnMessageDialogListener
import br.com.mining.mobile.shared.ui.viewmodels.MessageViewModel
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import org.jetbrains.anko.layoutInflater

class MessageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        fun openAtDialog(
            title: String, description: String, context: Context,
            listener: OnMessageDialogListener
        ): MaterialDialog {
            val view = context.layoutInflater.inflate(R.layout.view_message, null) as MessageView
            view.actionListener = listener
            view.title = title
            view.description = description

            view.initViewComponents()

            return MaterialDialog(context).show {
                customView(view = view, scrollable = false, noVerticalPadding = true)
                cancelOnTouchOutside(false)
            }
        }
    }


    private lateinit var binding: ViewMessageBinding
    private var actionListener: OnMessageDialogListener? = null
    private var title: String = ""
    private var description: String = ""
    private val viewModel: MessageViewModel by lazy { MessageViewModel() }

    private fun initViewComponents() {

        binding = DataBindingUtil.bind(this) ?: throw IllegalStateException()
        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.btnCancel.setOnClickListener { actionListener?.onClose() }
        binding.btnSuccess.setOnClickListener { actionListener }

        viewModel.title.set(title)
        viewModel.description.set(description)
    }
}

