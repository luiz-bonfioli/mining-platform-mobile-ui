package br.com.mining.mobile.framents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentStatusBinding
import br.com.mining.mobile.application.databinding.FragmentSuppliesBinding
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.viewmodels.fragments.StatusViewModel
import br.com.mining.mobile.viewmodels.fragments.SuppliesViewModel

class SuppliesFragment : MainBaseFragment() {

    private val viewModel: SuppliesViewModel by lazy {
        ViewModelProvider(this, SuppliesViewModel.Factory()).get(SuppliesViewModel::class.java)
    }

    private lateinit var binding: FragmentSuppliesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_supplies, container, false)
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}