package soutvoid.com.personalwallet.ui.util.delegates

import android.support.v7.app.AppCompatActivity
import kotlin.reflect.KProperty

/**
 * Created by andrew on 16.03.18.
 */
class ActivityIntentExtra<out T>(private val key: String) {

    private var value: T? = null
    private var valueInitialized = false

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: AppCompatActivity?, property: KProperty<*>): T? {
        if (!valueInitialized) {
            value = thisRef?.intent?.getSerializableExtra(key) as? T?
            valueInitialized = true
        }
        return value
    }

}

fun <T> extra(key: String): ActivityIntentExtra<T> = ActivityIntentExtra(key)