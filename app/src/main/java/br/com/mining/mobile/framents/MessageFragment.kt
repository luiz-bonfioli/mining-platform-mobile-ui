package br.com.mining.mobile.framents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.FragmentMessageBinding
import br.com.mining.mobile.framents.base.MainBaseFragment
import br.com.mining.mobile.viewmodels.fragments.MessageViewModel

class MessageFragment : MainBaseFragment() {

    private val viewModel: MessageViewModel by lazy {
        ViewModelProvider(this, MessageViewModel.Factory()).get(MessageViewModel::class.java)
    }

    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false)
        binding.viewModel = viewModel

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}