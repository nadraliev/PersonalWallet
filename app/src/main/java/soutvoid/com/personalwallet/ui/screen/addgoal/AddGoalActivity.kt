package soutvoid.com.personalwallet.ui.screen.addgoal

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import org.jetbrains.anko.intentFor
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.common.Saveable
import soutvoid.com.personalwallet.ui.util.attachFragment
import soutvoid.com.personalwallet.ui.util.delegates.extra
import soutvoid.com.personalwallet.util.GOAL_ID

class AddGoalActivity : ActivityWithToolbar() {

    companion object {
        fun getIntent(context: Context, goalLocalId: Long?) =
                context.intentFor<AddGoalActivity>(GOAL_ID to goalLocalId)

        private const val FRAGMENT_TAG = "addGoalFragment"
    }

    private val goalLocalId by extra<Long>(GOAL_ID)

    private var fragment: AddGoalFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.navigationIconResource = R.drawable.ic_arrow_back_dark
        val titleResId = if (goalLocalId == null) R.string.add_goal_title else R.string.edit_goal_title
        title = getString(titleResId)

        attachFragment()
    }

    private fun attachFragment() {
        fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as? AddGoalFragment?

        if (fragment == null) fragment = AddGoalFragment.newInstance(goalLocalId)

        fragment?.let {
            supportFragmentManager.attachFragment(it, R.id.container, FRAGMENT_TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.ok_menu_dark, menu)
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