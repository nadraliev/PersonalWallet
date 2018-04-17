package soutvoid.com.personalwallet.ui.screen.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.base.BaseActivity
import soutvoid.com.personalwallet.ui.util.attachFragment

class LoginActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent =
                Intent(context, LoginActivity::class.java)

        private const val FRAGMENT_TAG = "loginFragment"
    }

    private var fragment: LoginFragment? = null

    override fun getLayoutResId(): Int = R.layout.activity_common_with_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as? LoginFragment?

        if (fragment == null)
            fragment = LoginFragment.newInstance()

        fragment?.let {
            supportFragmentManager.attachFragment(it, R.id.container, FRAGMENT_TAG)
        }
    }
}