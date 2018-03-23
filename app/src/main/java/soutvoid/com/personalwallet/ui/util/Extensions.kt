package soutvoid.com.personalwallet.ui.util

import android.content.Context
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import soutvoid.com.personalwallet.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

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

/**
 * Get human readable representation of date
 * @receiver current time in millis
 */
fun Long.getHumanReadableDateShort(context: Context): String {
    val currentTime = Calendar.getInstance()
    val targetTime = Calendar.getInstance().apply { timeInMillis = this@getHumanReadableDateShort * 1000 }
    val diff = currentTime.timeInMillis - targetTime.timeInMillis
    if (diff >= 0 && diff < TimeUnit.DAYS.toMillis(1)) {
        return context.getString(R.string.common_today)
    } else if (diff >= 0 && diff < TimeUnit.DAYS.toMillis(2)) {
        return context.getString(R.string.common_yesterday)
    } else {
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
        return dateFormat.format(Date(this * 1000))
    }
}

fun getCurrencySymbol(): String = "\u20BD"