package br.com.mining.mobile.framents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentChecklistBinding
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.viewmodels.fragments.ChecklistViewModel

class ChecklistFragment : MainBaseFragment() {

    private val viewModel: ChecklistViewModel by lazy {
        ViewModelProvider(this, ChecklistViewModel.Factory()).get(ChecklistViewModel::class.java)
    }

    private lateinit var binding: FragmentChecklistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checklist, container, false)
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}