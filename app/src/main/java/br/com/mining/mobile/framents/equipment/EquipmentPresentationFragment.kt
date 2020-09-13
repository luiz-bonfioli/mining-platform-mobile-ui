package br.com.mining.mobile.framents.equipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentEquipmentPresentationBinding
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.viewmodels.fragments.EquipmentPresentationViewModel

class EquipmentPresentationFragment : MainBaseFragment() {

    private val viewModel: EquipmentPresentationViewModel by lazy {
        ViewModelProvider(this, EquipmentPresentationViewModel.Factory()).get(
            EquipmentPresentationViewModel::class.java
        )
    }

    private lateinit var binding: FragmentEquipmentPresentationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_equipment_presentation,
            container,
            false
        )
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btnRegistration.setOnClickListener {
            actionListener?.openFragment(newInstance(EquipmentFragment::class, actionListener))
        }
    }
}