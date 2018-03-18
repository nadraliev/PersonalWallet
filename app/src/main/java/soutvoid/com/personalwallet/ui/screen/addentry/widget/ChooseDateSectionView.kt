package soutvoid.com.personalwallet.ui.screen.addentry.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import soutvoid.com.personalwallet.R

/**
 * Created by andrew on 18.03.18.
 */
class ChooseDateSectionView : ConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.view_choose_date_section, this)
    }

}