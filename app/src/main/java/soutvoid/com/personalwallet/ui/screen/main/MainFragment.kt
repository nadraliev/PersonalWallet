package soutvoid.com.personalwallet.ui.screen.main

import android.content.Context
import android.os.Bundle
import android.view.View
import butterknife.BindView
import com.arellomobile.mvp.presenter.InjectPresenter
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.transactionentry.EntryType
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.screen.addentry.AddEntryActivity
import soutvoid.com.personalwallet.ui.screen.main.adapter.AddEntrySpeedDialAdapter
import uk.co.markormesher.android_fab.FloatingActionButton

class MainFragment : BaseFragment(), MainView {
    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }

    @InjectPresenter
    lateinit var mMainPresenter: MainPresenter

    @BindView(R.id.main_add_entry_fab)
    lateinit var addEntryFab: FloatingActionButton

    override fun getLayoutResId(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFabSpeedDial(view.context)
    }

    private fun initFabSpeedDial(context: Context) {
        addEntryFab.speedDialMenuAdapter = AddEntrySpeedDialAdapter(context) { entryType ->
            mMainPresenter.onAddEntry(entryType)
        }
    }

    override fun openAddEntry(entryType: EntryType) {
        context?.let {
            startActivity(AddEntryActivity.getIntent(it, entryType))
        }
    }
}
