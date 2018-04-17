package soutvoid.com.personalwallet.ui.screen.settings

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import soutvoid.com.personalwallet.ui.base.BaseView

interface SettingsView : BaseView {

    fun setSyncState(isSyncing: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun openLoginScreen()

}