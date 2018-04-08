package soutvoid.com.personalwallet.ui.screen.addentry

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.intentFor
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.common.Saveable
import soutvoid.com.personalwallet.ui.util.ENTRY_TYPE
import soutvoid.com.personalwallet.ui.util.TRANSACTION_ENTRY_ID
import soutvoid.com.personalwallet.ui.util.attachFragment
import soutvoid.com.personalwallet.ui.util.delegates.extra
import soutvoid.com.personalwallet.ui.util.doIfSdkAtLeast

/**
 * Created by andrew on 16.03.18.
 */
class AddEntryActivity : ActivityWithToolbar() {
    companion object {
        fun getIntent(context: Context, entryType: EntryType, dataId: Long? = null): Intent =
                context.intentFor<AddEntryActivity>(ENTRY_TYPE to entryType,
                        TRANSACTION_ENTRY_ID to dataId)

        const val FRAGMENT_TAG = "addEntryFragmentTag"
    }

    private val entryType by extra<EntryType>(ENTRY_TYPE)
    private val transactionEntry by extra<Long>(TRANSACTION_ENTRY_ID)
    private var fragment: AddEntryFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doIfSdkAtLeast(Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

        toolbar.textColor = R.color.md_white_1000
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.navigationIconResource = R.drawable.ic_arrow_back_light

        fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as AddEntryFragment?

        if (fragment == null) {

            entryType?.let {
                fragment = AddEntryFragment.newInstance(it, transactionEntry)
                supportFragmentManager.attachFragment(fragment!!,
                        R.id.container, FRAGMENT_TAG)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.ok_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_item_ok -> {
                (fragment as? Saveable?)?.save()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}