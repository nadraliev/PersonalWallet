package soutvoid.com.personalwallet.ui.util

import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by andrew on 16.03.18.
 */

fun FragmentManager.attachFragment(fragment: Fragment, containerViewId: Int, tag: String? = null) {
    beginTransaction().replace(containerViewId, fragment, tag).commit()
}

fun doIfSdkAtLeast(sdkInt: Int, action: () -> Unit) {
    if (Build.VERSION.SDK_INT >= sdkInt)
        action()
}