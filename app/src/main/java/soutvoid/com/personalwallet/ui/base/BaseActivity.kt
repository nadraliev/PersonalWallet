package soutvoid.com.personalwallet.ui.base

import android.os.Bundle
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatActivity
import com.orhanobut.logger.Logger
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.unfreezeInstanceState


abstract class BaseActivity : MvpAppCompatActivity() {
    companion object {
        const val TAG = "BaseActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("onCreate")
        unfreezeInstanceState(savedInstanceState)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        freezeInstanceState(outState)
    }
}
