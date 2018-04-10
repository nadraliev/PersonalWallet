package soutvoid.com.personalwallet.ui.screen.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.jetbrains.anko.appcompat.v7.navigationIconResource
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.common.ActivityWithToolbar
import soutvoid.com.personalwallet.ui.util.attachFragment

class CategoriesActivity : ActivityWithToolbar() {

    companion object {
        const val FRAGMENT_TAG = "categoriesTag"
        fun getIntent(context: Context): Intent = Intent(context, CategoriesActivity::class.java)
    }

    private var categoriesFragment: CategoriesFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        categoriesFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as CategoriesFragment?

        if (categoriesFragment == null)
            categoriesFragment = CategoriesFragment.newInstance()

        categoriesFragment?.let {
            supportFragmentManager.attachFragment(it, R.id.container, FRAGMENT_TAG)
        }
    }

    private fun initToolbar() {
        title = getString(R.string.categories_title)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        toolbar.navigationIconResource = R.drawable.ic_arrow_back_dark
    }
}