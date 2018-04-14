package soutvoid.com.personalwallet.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BasePresenter<T : BaseView>(kodein: Kodein? = null)
    : MvpPresenter<T>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val compositeDisposable = CompositeDisposable()

    init {
        kodein?.let { inject(it) }
    }

    infix fun BasePresenter<*>.subscribeTo(observable: Observable<*>) {
        compositeDisposable.add(observable.subscribe())
    }

    infix fun subscribeInBackgroundTo(observable: Observable<*>) {
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    infix fun subscribeWithoutFreezingTo(observable: Observable<*>) {
        observable.subscribe()
    }

    infix fun subscribeWithoutFreezingInBackgroundTo(observable: Observable<*>) {
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
