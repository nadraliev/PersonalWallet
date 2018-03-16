package soutvoid.com.personalwallet.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.orhanobut.logger.Logger

abstract class BaseFragment : MvpAppCompatFragment() {
    companion object {
        const val TAG = "BaseFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.d("onCreateView")
        val view = inflater.inflate(getLayoutResId(), container, false)
        ButterKnife.bind(this, view)
        return view
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
