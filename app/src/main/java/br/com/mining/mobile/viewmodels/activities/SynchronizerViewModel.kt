package br.com.mining.mobile.viewmodels.activities

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import br.com.mining.mobile.shared.listeners.ResultListener
import br.com.mining.mobile.viewmodels.states.DownloadState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

@SuppressLint("CheckResult")
class SynchronizerViewModel : ViewModel(), ResultListener {

    val progress = ObservableInt()
    val state = ObservableField<DownloadState>()

    override fun onSuccess() {
        state.set(DownloadState.SUCCESS)
    }

    override fun onError() {
        state.set(DownloadState.ERROR)
    }

    fun start() {
        state.set(DownloadState.PROCESS)
        Observable.range(1, 100)
            .concatMap { i -> Observable.just(i).delay(150, TimeUnit.MILLISECONDS) }
            .doOnNext { i ->
                this.progress.set(i)
                if (i == 100) {
                    onSuccess()
                }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()

    }

}