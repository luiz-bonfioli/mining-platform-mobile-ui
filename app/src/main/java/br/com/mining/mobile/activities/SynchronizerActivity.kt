package br.com.mining.mobile.activities

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.ActivitySynchronizerBinding
import br.com.mining.mobile.viewmodels.activities.SynchronizerViewModel
import br.com.mining.mobile.viewmodels.states.DownloadState
import org.jetbrains.anko.intentFor
import java.lang.ref.WeakReference

class SynchronizerActivity : BaseActivity() {

    private val viewModel: SynchronizerViewModel by lazy {
        ViewModelProvider(this).get(SynchronizerViewModel::class.java)
    }

    private val binding: ActivitySynchronizerBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_synchronizer
        ) as ActivitySynchronizerBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.progress.isIndeterminate = true
        viewModel.state.addOnPropertyChangedCallback(StateCallback(this))
        viewModel.start()
    }

    private fun cancelIndeterminate() {
        binding.progress.isIndeterminate = false
    }

    private fun handleState() {
        when (viewModel.state.get()) {
            DownloadState.ERROR, DownloadState.SUCCESS ->
                startActivity(intentFor<RegistrationActivity>())
            DownloadState.PROCESS ->
                cancelIndeterminate()
            DownloadState.INDETERMINANTE ->
                binding.progress.isIndeterminate = true
        }
    }

    //region ~~~~ StateCallback ~~~~
    private class StateCallback(view: SynchronizerActivity) :
        Observable.OnPropertyChangedCallback() {
        private val localInstance = WeakReference(view)

        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            localInstance.get()?.runOnUiThread {
                localInstance.get()?.handleState()
            }
        }
    }
}