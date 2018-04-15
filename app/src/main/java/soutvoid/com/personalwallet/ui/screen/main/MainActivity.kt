package soutvoid.com.personalwallet.ui.screen.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import butterknife.BindView
import com.tinsuke.icekick.extension.serialState
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.screen.checks.ChecksFragment
import soutvoid.com.personalwallet.ui.screen.goals.GoalsFragment
import soutvoid.com.personalwallet.ui.screen.history.HistoryFragment
import soutvoid.com.personalwallet.ui.screen.settings.SettingsFragment

class MainActivity : ActivityWithToolbar() {
    companion object {
        const val TAG = "MainActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    @BindView(R.id.main_bottom_navigation)
    lateinit var bottomNavigation: BottomNavigationView

    private val fragments: MutableMap<String, Fragment> = mutableMapOf()
    private var currentItem: BottomMenuItem by serialState(BottomMenuItem.HISTORY)

    override fun getLayoutResId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tryRecoverFragments(savedInstanceState)

        BottomMenuItem.values().forEach {
            fragments.getOrPut(it.name, it.createFragment)
        }

        val transaction = supportFragmentManager.beginTransaction()
                .runOnCommit {
                    bottomNavigation.setOnNavigationItemSelectedListener {
                        showFragmentByItemId(it.itemId)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
        fragments.filterNot { it.value.isAdded }.forEach { key, value ->
            transaction.add(R.id.container, value, key)
        }
        transaction.commit()

        showFragment(currentItem)
    }

    private fun tryRecoverFragments(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            BottomMenuItem.values().forEach { item ->
                val foundFragment = supportFragmentManager.findFragmentByTag(item.name)
                foundFragment?.let { fragments[item.name] = it }
            }
        }
    }

    private fun showFragmentByItemId(itemId: Int) {
        showFragment(when (itemId) {
            R.id.main_bottom_navigation_history -> BottomMenuItem.HISTORY
            R.id.main_bottom_navigation_goals -> BottomMenuItem.GOALS
            R.id.main_bottom_navigation_checks -> BottomMenuItem.CHECKS
            R.id.main_bottom_navigation_settings -> BottomMenuItem.SETTINGS
            else -> BottomMenuItem.HISTORY
        })
    }

    private fun showFragment(type: BottomMenuItem) {
        val fragment = fragments[type.name]
        fragment?.let {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.show(it)
            it.view?.isClickable = true
            fragments.filterNot { it.key == type.name }.forEach { key, value ->
                transaction.hide(value)
                value.view?.isClickable = false
            }
            transaction.commit()
            currentItem = type
        }
    }

    enum class BottomMenuItem(val createFragment: () -> Fragment) {
        HISTORY({ HistoryFragment.newInstance() }),
        GOALS({ GoalsFragment.newInstance() }),
        CHECKS({ ChecksFragment.newInstance() }),
        SETTINGS({ SettingsFragment.newInstance() })
    }
}
