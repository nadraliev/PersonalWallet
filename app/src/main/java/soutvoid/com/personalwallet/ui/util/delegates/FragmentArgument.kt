package soutvoid.com.personalwallet.ui.util.delegates

import android.support.v4.app.Fragment
import kotlin.reflect.KProperty

/**
 * Created by andrew on 16.03.18.
 */
class FragmentArgument<out T>(private val key: String) {

    private var value: T? = null
    private var valueInitialized = false

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Fragment?, property: KProperty<*>): T? {
        if (!valueInitialized) {
            value = thisRef?.arguments?.getSerializable(key) as? T?
            valueInitialized = true
        }
        return value
    }

}

fun <T> argument(key: String): FragmentArgument<T> = FragmentArgument(key)