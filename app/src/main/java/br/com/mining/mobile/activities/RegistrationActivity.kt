package br.com.mining.mobile.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import br.com.mining.mobile.application.R
import br.com.mining.mobile.application.databinding.ActivityRegistrationBinding
import br.com.mining.mobile.viewmodels.activities.RegistrationViewModel
import org.jetbrains.anko.intentFor

class RegistrationActivity : BaseActivity() {

    private val viewModel: RegistrationViewModel by lazy {
        ViewModelProvider(this).get(RegistrationViewModel::class.java)
    }

    private val binding: ActivityRegistrationBinding by lazy {
        DataBindingUtil.setContentView(
            this,
            R.layout.activity_registration
        ) as ActivityRegistrationBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        binding.btnRegistration.setOnClickListener {
            startActivity(intentFor<SettingActivity>())
            finish()
        }
    }


}