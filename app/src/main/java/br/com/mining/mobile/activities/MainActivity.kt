package br.com.mining.mobile.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.ActivityMainBinding
import br.com.mining.mobile.application.databinding.ActivityRegistrationBinding
import br.com.mining.mobile.framents.*
import br.com.mining.mobile.framents.base.BaseFragment
import br.com.mining.mobile.viewmodels.activities.MainViewModel
import br.com.mining.mobile.viewmodels.activities.RegistrationViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.reflect.KClass

class MainActivity : BaseFragmentActivity(), BottomNavigationView.OnNavigationItemSelectedListener {


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.navigationView.setOnNavigationItemSelectedListener(this)
        openMenuFragment(HomeFragment::class)
    }

    override fun close() {
        finish()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_activity ->
                openMenuFragment(HomeFragment::class)
            R.id.navigation_msgs ->
                openMenuFragment(MessageFragment::class)
            R.id.navigation_status ->
                openMenuFragment(StatusFragment::class)
            R.id.navigation_fuel ->
                openMenuFragment(SuppliesFragment::class)
            R.id.navigation_settings ->
                openMenuFragment(SettingsFragment::class)
        }
        return true
    }

    override fun currentFragment(fragment: BaseFragment) {
        when {
            fragment as? HomeFragment != null ->
                binding.navigationView.menu.findItem(R.id.navigation_activity).isChecked = true
            fragment as? MessageFragment != null ->
                binding.navigationView.menu.findItem(R.id.navigation_msgs).isChecked = true
            fragment as? StatusFragment != null ->
                binding.navigationView.menu.findItem(R.id.navigation_status).isChecked = true
            fragment as? SuppliesFragment != null ->
                binding.navigationView.menu.findItem(R.id.navigation_fuel).isChecked = true
            fragment as? SettingsFragment != null ->
                binding.navigationView.menu.findItem(R.id.navigation_settings).isChecked = true
        }
    }

    private fun <T : BaseFragment> openMenuFragment(clazz: KClass<T>) {
        openFragment(BaseFragment.newInstance(clazz, this))
    }
}