package soutvoid.com.personalwallet.ui.screen.checks

import soutvoid.com.personalwallet.ui.base.BaseView
import java.io.File

interface ChecksView : BaseView {
    fun takeNewPhoto()
    fun showChecks(checks: List<File>)
    fun getAllChecks(callback: (List<File>) -> Unit)
}