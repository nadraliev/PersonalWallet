package soutvoid.com.personalwallet.ui.screen.login

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment

class LoginFragment : BaseFragment(), LoginView {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter = LoginPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_login
}