package soutvoid.com.personalwallet.ui.util

import com.github.salomonbrys.kodein.bindings.Scope
import com.github.salomonbrys.kodein.bindings.ScopeRegistry
import soutvoid.com.personalwallet.ui.base.BasePresenter
import java.util.*

/**
 * Created by andrew on 17.03.18.
 */
object PresenterScope : Scope<BasePresenter<*>> {

    private val scopeRegistries: WeakHashMap<BasePresenter<*>, ScopeRegistry> = WeakHashMap()

    override fun getRegistry(context: BasePresenter<*>): ScopeRegistry =
            scopeRegistries.getOrPut(context) { ScopeRegistry() } as ScopeRegistry
}