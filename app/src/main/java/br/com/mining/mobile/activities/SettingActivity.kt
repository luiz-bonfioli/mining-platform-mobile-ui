package br.com.mining.mobile.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.ActivitySettingBinding
import br.com.mining.mobile.framents.base.BaseFragment
import br.com.mining.mobile.framents.equipment.EquipmentFragment
import br.com.mining.mobile.framents.equipment.EquipmentPresentationFragment
import br.com.mining.mobile.viewmodels.activities.SelectEquipmentViewModel
import kotlin.reflect.KClass

class SettingActivity : BaseFragmentActivity() {


    private val viewModel: SelectEquipmentViewModel by lazy {
        ViewModelProvider(this).get(SelectEquipmentViewModel::class.java)
    }

    private val binding: ActivitySettingBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_setting
        ) as ActivitySettingBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        openMenuFragment(EquipmentFragment::class)
    }

    override fun currentFragment(fragment: BaseFragment) {
    }

    override fun close() {
        finish()
    }

    private fun <T : BaseFragment> openMenuFragment(clazz: KClass<T>) {
        openFragment(BaseFragment.newInstance(clazz, this))
    }
}