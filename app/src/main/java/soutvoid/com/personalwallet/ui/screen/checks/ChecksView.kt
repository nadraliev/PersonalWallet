package soutvoid.com.personalwallet.ui.screen.checks

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import soutvoid.com.personalwallet.ui.base.BaseView
import java.io.File

interface ChecksView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun takeNewPhoto()
    fun showChecks(checks: List<File>)
    fun getAllChecks(callback: (List<File>) -> Unit)
    @StateStrategyType(SkipStrategy::class)
    fun openCheckFullscreen(file: File)
}