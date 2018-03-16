package soutvoid.com.personalwallet.ui.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.util.attachFragment


class MainActivity : ActivityWithToolbar() {
    companion object {
        const val TAG = "MainActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.attachFragment(MainFragment.newInstance(), R.id.container)
    }
}
