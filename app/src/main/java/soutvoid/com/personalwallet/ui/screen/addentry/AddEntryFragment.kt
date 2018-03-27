package soutvoid.com.personalwallet.ui.screen.addentry

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.presenter.ProvidePresenterTag
import org.jetbrains.anko.support.v4.withArguments
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.transactionentry.Category
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.domain.transactionentry.Income
import soutvoid.com.personalwallet.domain.transactionentry.Outcome
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.common.Saveable
import soutvoid.com.personalwallet.ui.screen.addentry.data.NewEntryData
import soutvoid.com.personalwallet.ui.screen.addentry.widget.ChooseCategorySectionView
import soutvoid.com.personalwallet.ui.screen.addentry.widget.ChooseDateSectionView
import soutvoid.com.personalwallet.ui.screen.addentry.widget.InputNameSectionView
import soutvoid.com.personalwallet.ui.screen.addentry.widget.InputValueSectionView
import soutvoid.com.personalwallet.ui.util.ENTRY_TYPE
import soutvoid.com.personalwallet.ui.util.delegates.argument
import soutvoid.com.personalwallet.ui.util.doIfSdkAtLeast
import soutvoid.com.personalwallet.ui.util.getColorResId
import soutvoid.com.personalwallet.ui.util.getDarkColorResId

class AddEntryFragment : BaseFragment(), AddEntryView, Saveable {
    companion object {
        const val TAG = "AddEntryFragment"

        fun newInstance(entryType: EntryType): AddEntryFragment =
                AddEntryFragment().withArguments(ENTRY_TYPE to entryType)
    }

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var mAddEntryPresenter: AddEntryPresenter

    @BindView(R.id.add_entry_name_section)
    lateinit var nameSection: InputNameSectionView
    @BindView(R.id.add_entry_value_section)
    lateinit var valueSection: InputValueSectionView
    @BindView(R.id.add_entry_category_section)
    lateinit var categorySection: ChooseCategorySectionView
    @BindView(R.id.add_entry_date_section)
    lateinit var dateSection: ChooseDateSectionView

    private val entryType by argument<EntryType>(ENTRY_TYPE)

    @ProvidePresenterTag(presenterClass = AddEntryPresenter::class, type = PresenterType.WEAK)
    fun provideAddEntryPresenterTag(): String = entryType.toString()

    @ProvidePresenter(type = PresenterType.WEAK)
    fun provideAddEntryPresenter(): AddEntryPresenter {
        entryType!!.let { return AddEntryPresenter(App.instance.kodein, it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
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

    override fun setAvailableCategories(categories: List<Category>) {
        categorySection.categories = categories
    }

    override fun chooseCategory(name: String) {
        categorySection.selectCategory(name)
    }

    override fun save() {
        mAddEntryPresenter.onSaveClicker(gatherData())
    }

    override fun finish() {
        activity?.finish()
    }

    override fun showBlankNameError(show: Boolean) {
        nameSection.nameInput.error = context?.getString(R.string.add_entry_blank_name_error)
        nameSection.nameInput.isErrorEnabled = show
    }

    override fun showInvalidValueError(show: Boolean) {
        valueSection.valueInput.error = context?.getString(R.string.add_entry_invalid_value_error)
        valueSection.valueInput.isErrorEnabled = show
    }

    private fun gatherData(): NewEntryData =
            NewEntryData(nameSection.text, categorySection.categories[categorySection.currentCategoryIndex],
                    dateSection.calendar.timeInMillis, valueSection.valueText)


    private fun setListeners() {
        categorySection.onNewCategoryInputListener = { mAddEntryPresenter.onNewCategoryEntered(it) }
    }
}
