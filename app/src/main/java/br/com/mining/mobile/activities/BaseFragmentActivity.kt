package br.com.mining.mobile.activities

import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.mining.mobile.activities.listeners.OnFragmentActivityActionListener
import br.com.mining.mobile.application.R
import br.com.mining.mobile.framents.base.BaseFragment

abstract class BaseFragmentActivity : BaseActivity(), OnFragmentActivityActionListener {

    private var backButtonEnable: Boolean = true
    private var title: TextView? = null

    abstract fun currentFragment(fragment: BaseFragment)

    abstract fun close()

    override fun onBackPressed() {
        if (backButtonEnable) {
            popFragment(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        popFragment(true)
        return true
    }

    private fun popFragment(closesActivity: Boolean = false) {
        runOnUiThread {
            supportFragmentManager.popBackStackImmediate()

            for (frag in supportFragmentManager.fragments.reversed()) {
                if (BaseFragment::class.java.isAssignableFrom(frag::class.java)) {
                    frag.onResume()
                    (frag as? BaseFragment)?.executePendingActionsNotResumed()
                    break
                }
            }

            if (supportFragmentManager.fragments.isNotEmpty()) {
                currentFragment(supportFragmentManager.fragments.last() as BaseFragment)
            } else if (closesActivity && supportFragmentManager.fragments.isEmpty()) {
                close()
            }
        }
    }

    fun setTitleActionBar(titleView: TextView) {
        title = titleView
    }

    //region ~~~~ OnFragmentActivityActionListener Interface ~~~~
    override fun openFragment(fragment: Fragment, dismissBack: Boolean) {
        val tag = System.currentTimeMillis().toString()

        if (dismissBack) popFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun closeCurrentFragment() {
        popFragment(true)
    }
//endregion

}