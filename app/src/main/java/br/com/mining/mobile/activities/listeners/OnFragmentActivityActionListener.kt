package br.com.mining.mobile.activities.listeners

import androidx.fragment.app.Fragment

interface OnFragmentActivityActionListener {

    fun openFragment(fragment: Fragment, dismissBack: Boolean = false)

    fun closeCurrentFragment()

}
