package br.com.mining.mobile.framents.equipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mining.mobile.activities.RegistrationActivity
import br.com.mining.mobile.adapters.EquipmentAdapter
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentEquipmentBinding
import br.com.mining.mobile.framents.ChecklistFragment
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.shared.model.Checklist
import br.com.mining.mobile.viewmodels.fragments.EquipmentViewModel
import br.com.mining.mobile.viewmodels.items.EquipmentItemViewModel
import com.unikode.mobile.fragments.listeners.OnRecyclerViewClickListener
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class EquipmentFragment : MainBaseFragment(), OnRecyclerViewClickListener<EquipmentItemViewModel> {

    companion object {
        private const val SPAN_COUNT = 7
    }

    private val viewModel: EquipmentViewModel by lazy {
        ViewModelProvider(this, EquipmentViewModel.Factory(adapter)).get(
            EquipmentViewModel::class.java
        )
    }

    private val adapter: EquipmentAdapter by lazy { EquipmentAdapter(this) }

    private lateinit var binding: FragmentEquipmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_equipment,
            container,
            false
        )
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setOnRefreshListener { onResume() }
        binding.recyclerEquipments.adapter = adapter
        binding.recyclerEquipments.layoutManager = GridLayoutManager(context, SPAN_COUNT, RecyclerView.VERTICAL, false)
        viewModel.starList()

        binding.swipeRefresh.onRefresh {
            binding.swipeRefresh.isRefreshing = false
        }
    }


    override fun onItemSelected(item: EquipmentItemViewModel, position: Int) {
        activity?.startActivity(intentFor<RegistrationActivity>())
        activity?.finish()
    }
}