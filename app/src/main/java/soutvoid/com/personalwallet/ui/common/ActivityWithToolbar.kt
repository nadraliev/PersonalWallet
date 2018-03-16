package soutvoid.com.personalwallet.ui.common

import android.os.Bundle
import android.view.View
import butterknife.BindView
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.base.BaseActivity
import soutvoid.com.personalwallet.ui.common.widget.AppToolbar

/**
 * Created by andrew on 16.03.18.
 */
abstract class ActivityWithToolbar : BaseActivity() {

    @BindView(R.id.toolbar)
    lateinit var toolbar: AppToolbar

    @BindView(R.id.container)
    lateinit var container: View

    override fun getLayoutResId(): Int = R.layout.activity_common_with_toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
    }
}