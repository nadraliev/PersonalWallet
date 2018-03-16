package soutvoid.com.personalwallet.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.ButterKnife

import com.arellomobile.mvp.presenter.InjectPresenter

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.orhanobut.logger.Logger


abstract class BaseActivity : MvpAppCompatActivity(), BaseView {
    companion object {
        const val TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("onCreate")
        setContentView(getLayoutResId())
        ButterKnife.bind(this)
    }

    override fun onStop() {
        Logger.d("onStop")
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        Logger.d("onStart")
    }

    override fun onDestroy() {
        Logger.d("onDestroy")
        super.onDestroy()
    }

    protected abstract fun getLayoutResId(): Int
}
