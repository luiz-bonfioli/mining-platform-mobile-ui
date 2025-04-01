package br.com.mining.mobile.framents.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import br.com.mining.mobile.activities.listeners.OnFragmentActivityActionListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

open class BaseFragment : Fragment() {

    companion object {
        const val STOP_STATE = "stop_state"

        fun <T : BaseFragment> newInstance(clazz: KClass<T>, listener: OnFragmentActivityActionListener? = null): T =
            clazz.java.newInstance().apply {
                this.actionListener = listener
            }
    }

    protected var actionListener: OnFragmentActivityActionListener? = null

    private var stateQueue: Queue<() -> Unit> = ArrayDeque()
    private var isStopFragment: AtomicBoolean = AtomicBoolean(false)
    private var enableToExecuteActionsAtResume: Boolean = true

    private var job: Job? = null

    //region ~~~~ Fragment Lifecycle ~~~~
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(STOP_STATE, true)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        isStopFragment.set(true)
    }

    override fun onResume() {
        super.onResume()
        isStopFragment.set(false)

        if (enableToExecuteActionsAtResume) {
            executePendingActions()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (job?.isCancelled == false) {
            job?.cancel()
        }
    }
    //endregion

    fun <T : BaseFragment> openFragment(fragmentClass: KClass<T>) {
        actionListener?.openFragment(
            newInstance(
                fragmentClass,
                actionListener
            )
        )
    }

    fun isReturningFromStop(savedInstanceState: Bundle?): Boolean =
        savedInstanceState == null || !savedInstanceState.getBoolean(STOP_STATE, false)

    private fun queueAction(method: () -> Unit) {
        if (isRunning()) {
            method()
        } else {
            stateQueue.offer(method)
        }
    }

    fun executePendingActionsNotResumed() {
        if (!enableToExecuteActionsAtResume) {
            executePendingActions()
        }
    }

    open fun dismiss() {
        if (isRunning()) {
            actionListener?.closeCurrentFragment()
        } else {
            queueAction(this::dismiss)
        }
    }

    protected fun disableResumeActionsExecution() {
        enableToExecuteActionsAtResume = false
    }

    private fun executePendingActions() {
        if (!stateQueue.isEmpty()) {
            job = GlobalScope.launch {
                isStopFragment.set(false)

                while (!stateQueue.isEmpty()) {
                    if (isRunning()) {
                        stateQueue.poll()?.invoke()
                    }
                }
            }
        }
    }

    private fun isRunning(): Boolean = isAdded && !isStopFragment.get()
}
