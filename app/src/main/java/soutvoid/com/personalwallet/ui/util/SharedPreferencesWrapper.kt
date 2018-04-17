package soutvoid.com.personalwallet.ui.util

import android.content.Context
import android.content.Context.MODE_PRIVATE

class SharedPreferencesWrapper(context: Context) {

    companion object {
        const val SHARED_PREFERENCES_NAME = "main"
    }

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    var isSyncing: Boolean
        get() = sharedPreferences.getBoolean("isSyncing", false)
        set(value) {
            sharedPreferences.edit().putBoolean("isSyncing", value).apply()
        }

    var userId: Long
        get() = sharedPreferences.getLong("userId", -1L)
        set(value) {
            sharedPreferences.edit().putLong("userId", value).apply()
        }

}