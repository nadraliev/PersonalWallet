package soutvoid.com.personalwallet.ui.common.widget

import android.content.Context
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.AttributeSet

/**
 * Created by andrew on 16.03.18.
 */
class AppToolbar : Toolbar {

    @ColorRes
    var textColor: Int = 0
        set(value) {
            val color = ContextCompat.getColor(context, value)
            setSubtitleTextColor(color)
            setTitleTextColor(color)
        }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

    }
}