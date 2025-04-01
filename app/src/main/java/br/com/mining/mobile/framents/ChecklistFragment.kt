package br.com.mining.mobile.framents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.mining.mobile.activities.MainActivity
import br.com.mining.mobile.activities.SettingActivity
import br.com.mining.mobile.adapters.ChecklistAdapter
import br.com.mining.mobile.adapters.EquipmentAdapter
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentChecklistBinding
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.viewmodels.fragments.ChecklistViewModel
import br.com.mining.mobile.viewmodels.items.ChecklistItemViewModel
import com.unikode.mobile.fragments.listeners.OnRecyclerViewClickListener
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class ChecklistFragment : MainBaseFragment(), OnRecyclerViewClickListener<ChecklistItemViewModel> {

    private val adapter: ChecklistAdapter by lazy { ChecklistAdapter(requireContext(), this) }

    private val viewModel: ChecklistViewModel by lazy {
        ViewModelProvider(this, ChecklistViewModel.Factory(adapter)).get(ChecklistViewModel::class.java)
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

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.recyclerChecklist.adapter = adapter
        binding.recyclerChecklist.layoutManager = layoutManager

        binding.swipeRefresh.onRefresh {
            binding.swipeRefresh.isRefreshing = false
        }
        viewModel.starList()

        binding.btnNext.setOnClickListener {
            startActivity(intentFor<MainActivity>())
            dismiss()
        }
    }

    override fun onItemSelected(item: ChecklistItemViewModel, position: Int) {

    }
}