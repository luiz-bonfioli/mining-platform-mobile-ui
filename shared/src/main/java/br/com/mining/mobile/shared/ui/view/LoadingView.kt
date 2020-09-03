package br.com.mining.mobile.shared.ui.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import br.com.mining.mobile.shared.R
import br.com.mining.mobile.shared.databinding.ViewLoadingBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import org.jetbrains.anko.layoutInflater

class LoadingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        fun openAtDialog(context: Context, title: String): MaterialDialog {
            val binding: ViewLoadingBinding = DataBindingUtil.inflate(context.layoutInflater, R.layout.view_loading, null, false)
            val view = binding.viewLoading

            binding.title = title

            return MaterialDialog(context).show {
                customView(view = view, scrollable = false, noVerticalPadding = true)
                cancelOnTouchOutside(false)
                cancelable(false)
            }
        }
    }

}
