package br.com.mining.mobile.framents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentSettingsBinding
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.viewmodels.fragments.SettingsViewModel

class SettingsFragment : MainBaseFragment() {

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this, SettingsViewModel.Factory()).get(SettingsViewModel::class.java)
    }

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}