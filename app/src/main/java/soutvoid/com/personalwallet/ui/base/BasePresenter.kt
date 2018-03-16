package soutvoid.com.personalwallet.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector

abstract class BasePresenter<T : BaseView>(kodein: Kodein? = null)
    : MvpPresenter<T>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    init {
        kodein?.let { inject(it) }
    }
}
