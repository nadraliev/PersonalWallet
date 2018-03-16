package soutvoid.com.personalwallet.ui.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.base.BaseActivity


class MainActivity : BaseActivity(), MainView {
    companion object {
        const val TAG = "MainActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    @InjectPresenter
    lateinit var mMainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutResId(): Int = R.layout.activity_main
}
