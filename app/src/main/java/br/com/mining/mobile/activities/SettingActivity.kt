package br.com.mining.mobile.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.activities.enums.ConfigurationOpenMode
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.ActivitySettingBinding
import br.com.mining.mobile.framents.ChecklistFragment
import br.com.mining.mobile.framents.base.BaseFragment
import br.com.mining.mobile.framents.equipment.EquipmentFragment
import br.com.mining.mobile.framents.equipment.EquipmentPresentationFragment
import br.com.mining.mobile.viewmodels.activities.SelectEquipmentViewModel
import kotlin.reflect.KClass

class SettingActivity : BaseFragmentActivity() {

    companion object {
        const val CONFIGURATION_OPEN_MODE = "config-open-mode"
    }

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

        val openMode = if (intent.hasExtra(CONFIGURATION_OPEN_MODE)) {
            intent.getSerializableExtra(CONFIGURATION_OPEN_MODE) as? ConfigurationOpenMode
        } else {
            ConfigurationOpenMode.SELECT_EQUIPMENT
        }

        val baseFragment :  Fragment = when (openMode) {
            ConfigurationOpenMode.CHECKLIST -> BaseFragment.newInstance(ChecklistFragment::class, this)
            ConfigurationOpenMode.SELECT_EQUIPMENT -> BaseFragment.newInstance(EquipmentFragment::class, this)
            else -> BaseFragment.newInstance(EquipmentFragment::class, this)
        }
        openFragment(baseFragment)

    }

    override fun currentFragment(fragment: BaseFragment) {
    }

    override fun close() {
        finish()
    }
}