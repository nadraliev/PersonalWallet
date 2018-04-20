package soutvoid.com.personalwallet.ui.screen.login

import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.util.setBlocked

class LoginFragment : BaseFragment(), LoginView {

    companion object {
        fun newInstance(): LoginFragment = LoginFragment()
    }

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @BindView(R.id.login_email_text_input)
    lateinit var emailTextInput: TextInputLayout
    @BindView(R.id.login_password_text_input)
    lateinit var passwordTextInput: TextInputLayout
    @BindView(R.id.login_progress_bar)
    lateinit var progressBar: ProgressBar
    @BindView(R.id.login_login_btn)
    lateinit var loginBtn: View
    @BindView(R.id.login_register_btn)
    lateinit var registerBtn: View
    @BindView(R.id.login_error_text)
    lateinit var errorText: TextView

    @ProvidePresenter
    fun providePresenter(): LoginPresenter = LoginPresenter(App.instance.kodein)

    override fun getLayoutResId(): Int = R.layout.fragment_login

    override fun showEmailIsInvalidError() {
        emailTextInput.isErrorEnabled = true
        emailTextInput.error = getString(R.string.login_email_is_invalid_error)
    }

    override fun showPasswordIsInvalidError() {
        passwordTextInput.isErrorEnabled = true
        passwordTextInput.error = getString(R.string.login_password_is_invalid_error)
    }

    override fun showPasswordTooSmallError() {
        passwordTextInput.isErrorEnabled = true
        passwordTextInput.error = getString(R.string.login_password_too_small_error)
    }

    override fun showLoginError() {
        errorText.text = getString(R.string.login_login_or_password_is_incorrect_error)
        errorText.visibility = View.VISIBLE
    }

    override fun hideLoginError() {
        errorText.visibility = View.INVISIBLE
    }

    override fun setButtonsBlocked(blocked: Boolean) {
        loginBtn.setBlocked(blocked)
        registerBtn.setBlocked(blocked)
    }

    override fun setProgressEnabled(enabled: Boolean) {
        progressBar.visibility = if (enabled) View.VISIBLE else View.INVISIBLE
    }

    override fun finish() {
        activity?.finish()
    }

    @OnClick(R.id.login_login_btn)
    fun onLoginBtnClick() {
        presenter.onLoginClick(emailTextInput.editText?.text?.toString() ?: "",
                passwordTextInput.editText?.text?.toString() ?: "")
    }

    @OnClick(R.id.login_register_btn)
    fun onRegisterBtnClick() {
        presenter.onRegisterClick(emailTextInput.editText?.text?.toString() ?: "",
                passwordTextInput.editText?.text?.toString() ?: "")
    }

    @OnClick(R.id.login_back_button_container)
    fun onBackButtonClick() {
        activity?.onBackPressed()
    }

}