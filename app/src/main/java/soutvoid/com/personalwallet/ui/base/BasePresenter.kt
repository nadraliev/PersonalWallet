package soutvoid.com.personalwallet.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BasePresenter<T : BaseView>(kodein: Kodein? = null)
    : MvpPresenter<T>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val compositeDisposable = CompositeDisposable()

    init {
        kodein?.let { inject(it) }
    }

    fun <T> Observable<T>.subscribeTo(onNext: ((T) -> Unit)? = null,
                                      onError: ((Throwable) -> Unit)? = null,
                                      onComplete: (() -> Unit)? = null) {
        compositeDisposable.add(doSubscribe(onNext, onError, onComplete))
    }

    fun <T> Observable<T>.subscribeAsyncTo(onNext: ((T) -> Unit)? = null,
                                           onError: ((Throwable) -> Unit)? = null,
                                           onComplete: (() -> Unit)? = null) {
        compositeDisposable.add(async().doSubscribe(onNext, onError, onComplete))
    }

    fun <T> Observable<T>.subscribeWithoutFreezingTo(onNext: ((T) -> Unit)? = null,
                                                     onError: ((Throwable) -> Unit)? = null,
                                                     onComplete: (() -> Unit)? = null) {
        doSubscribe(onNext, onError, onComplete)
    }

    fun <T> Observable<T>.subscribeWithoutFreezingAsyncTo(onNext: ((T) -> Unit)? = null,
                                                          onError: ((Throwable) -> Unit)? = null,
                                                          onComplete: (() -> Unit)? = null) {
        async().doSubscribe(onNext, onError, onComplete)
    }

    fun <T> Observable<T>.async(): Observable<T> =
            subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    private fun <T> Observable<T>.doSubscribe(onNext: ((T) -> Unit)? = null,
                                              onError: ((Throwable) -> Unit)? = null,
                                              onComplete: (() -> Unit)? = null): Disposable =
            subscribe(
                    { onNext?.invoke(it) },
                    { onError?.invoke(it) },
                    { onComplete?.invoke() }
            )

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
