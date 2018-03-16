package soutvoid.com.personalwallet.ui.screen.addentry

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.intentFor
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.util.ENTRY_TYPE
import soutvoid.com.personalwallet.ui.util.attachFragment
import soutvoid.com.personalwallet.ui.util.delegates.extra
import soutvoid.com.personalwallet.ui.util.doIfSdkAtLeast

/**
 * Created by andrew on 16.03.18.
 */
class AddEntryActivity : ActivityWithToolbar() {
    companion object {
        fun getIntent(context: Context, entryType: EntryType): Intent =
                context.intentFor<AddEntryActivity>(ENTRY_TYPE to entryType)
    }

    private val entryType by extra<EntryType>(ENTRY_TYPE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doIfSdkAtLeast(Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }

        toolbar.textColor = R.color.md_white_1000
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.navigationIconResource = R.drawable.ic_arrow_back_light

        entryType?.let {
            supportFragmentManager.attachFragment(AddEntryFragment.newInstance(it),
                    R.id.container)
        }
    }
}