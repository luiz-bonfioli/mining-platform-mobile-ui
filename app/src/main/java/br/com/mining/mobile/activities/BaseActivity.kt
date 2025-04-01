package br.com.mining.mobile.activities

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import org.jetbrains.anko.findOptional

abstract class BaseActivity : AppCompatActivity() {

    val parentView: View? by lazy { findOptional<View>(android.R.id.content) }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

}