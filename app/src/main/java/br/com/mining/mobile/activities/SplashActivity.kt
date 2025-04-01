package br.com.mining.mobile.activities

import android.Manifest
import android.os.Bundle
import br.com.mining.mobile.application.R
import com.afollestad.materialdialogs.MaterialDialog
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.intentFor
import permissions.dispatcher.*
import permissions.dispatcher.ktx.withPermissionsCheck
import java.util.*
import kotlin.concurrent.schedule

@RuntimePermissions
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        showPermission()
    }

    @NeedsPermission(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun showPermission() {
        Timer().schedule(4000) {
            startActivity(intentFor<SynchronizerActivity>())
            finish()
        }
    }

    @OnShowRationale(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun showRationaleForPermission(request: PermissionRequest) {
        MaterialDialog(this).show {
            title(R.string.permission_title)
            message(R.string.permission_text)
            positiveButton(R.string.ok)
        }
    }

    @OnPermissionDenied(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onPermissionDenied() {
        parentView?.snackbar(R.string.permission_denied)
    }

    @OnNeverAskAgain(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onPermissionNeverAskAgain() {
        parentView?.snackbar(R.string.permission_never_askagain)
        showPermission()
    }
}