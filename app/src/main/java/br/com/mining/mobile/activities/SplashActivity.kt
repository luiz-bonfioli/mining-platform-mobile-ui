package br.com.mining.mobile.activities

import android.os.Bundle
import br.com.mining.mobile.application.R
import org.jetbrains.anko.intentFor
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer().schedule(4000) {
                startActivity(intentFor<SynchronizerActivity>())
            finish()
        }
    }
}