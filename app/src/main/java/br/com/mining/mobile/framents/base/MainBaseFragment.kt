package br.com.mining.mobile.framents.base

import br.com.mining.mobile.activities.MainActivity

open class MainBaseFragment : BaseFragment() {

    protected val mainActivity: MainActivity? by lazy { activity as? MainActivity }

}
