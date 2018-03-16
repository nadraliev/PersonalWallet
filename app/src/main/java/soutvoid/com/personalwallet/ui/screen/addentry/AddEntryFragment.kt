package soutvoid.com.personalwallet.ui.screen.addentry

import android.os.Build
import android.support.v4.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import org.jetbrains.anko.support.v4.withArguments
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.Income
import soutvoid.com.personalwallet.domain.transactionentry.Outcome
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.util.ENTRY_TYPE
import soutvoid.com.personalwallet.ui.util.delegates.argument
import soutvoid.com.personalwallet.ui.util.doIfSdkAtLeast
import soutvoid.com.personalwallet.ui.util.getColorResId
import soutvoid.com.personalwallet.ui.util.getDarkColorResId

class AddEntryFragment : BaseFragment(), AddEntryView {
    companion object {
        const val TAG = "AddEntryFragment"

        fun newInstance(entryType: EntryType): AddEntryFragment =
                AddEntryFragment().withArguments(ENTRY_TYPE to entryType)
    }

    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var mAddEntryPresenter: AddEntryPresenter

    private val entryType by argument<EntryType>(ENTRY_TYPE)

    @ProvidePresenter(type = PresenterType.GLOBAL)
    fun provideAddEntryPresenter(): AddEntryPresenter {
        entryType!!.let { return AddEntryPresenter(App.instance.kodein, it) }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_add_entry

    override fun setToolbarColorForEntryType(entryType: EntryType) {
        val colorResId = entryType.getColorResId()
        (activity as? ActivityWithToolbar)?.let {
            it.toolbar.setBackgroundColor(ContextCompat.getColor(it, colorResId))
        }
    }

    override fun setStatusBarColorForEntryType(entryType: EntryType) {
        val colorResId = entryType.getDarkColorResId()
        (activity as? ActivityWithToolbar)?.let {
            doIfSdkAtLeast(Build.VERSION_CODES.LOLLIPOP) {
                it.window.statusBarColor = ContextCompat.getColor(it, colorResId)
            }
        }
    }

    override fun setTitleForEntryType(entryType: EntryType) {
        val stringResId = when (entryType) {
            is Income -> R.string.add_entry_income_title
            is Outcome -> R.string.add_entry_outcome_title
        }
        activity?.setTitle(stringResId)
    }
}
