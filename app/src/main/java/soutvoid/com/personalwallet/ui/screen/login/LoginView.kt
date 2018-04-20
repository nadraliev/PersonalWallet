package soutvoid.com.personalwallet.ui.screen.login

import soutvoid.com.personalwallet.ui.base.BaseView

interface LoginView : BaseView {
    fun showEmailIsInvalidError()
    fun showPasswordIsInvalidError()
    fun showPasswordTooSmallError()
    fun showLoginError()
    fun hideLoginError()
    fun setButtonsBlocked(blocked: Boolean)
    fun setProgressEnabled(enabled: Boolean)
    fun finish()
}