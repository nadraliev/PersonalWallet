package soutvoid.com.personalwallet.ui.common.widget

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import soutvoid.com.personalwallet.R

/**
 * @author Andrew Nadraliev
 * @created on 12/18/17
 */
class TextSubtextView : FrameLayout {

    @BindView(R.id.common_text_subtext_container)
    lateinit var container: View
    @BindView(R.id.common_main_text)
    lateinit var textTv: TextView
    @BindView(R.id.common_subtext)
    lateinit var subtextTv: TextView

    var text: String
        get() = textTv.text.toString()
        set(value) {
            textTv.text = value
        }

    var subtext: String
        get() = subtextTv.text.toString()
        set(value) {
            subtextTv.text = value
        }

    constructor(context: Context?)
            : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?)
            : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    @TargetApi(21)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context?,
                     attrs: AttributeSet? = null,
                     defStyleAttr: Int = 0,
                     defStyleRes: Int = 0) {
        val layoutId = R.layout.common_text_subtext_view

        val view = View.inflate(context, layoutId, this)
        ButterKnife.bind(this, view)

        if (attrs != null) {
            readAttr(attrs, defStyleAttr, defStyleRes)
        }
    }

    private fun readAttr(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
        val typedArray = context?.theme?.obtainStyledAttributes(attrs,
                R.styleable.TextSubtextView,
                defStyleAttr, defStyleRes)

        if (typedArray != null) {
            try {
                val text = typedArray.getString(R.styleable.TextSubtextView_mainText)
                val subtext = typedArray.getString(R.styleable.TextSubtextView_subtext)
                text?.let { textTv.text = it }
                subtext?.let { subtextTv.text = it }
            } finally {
                typedArray.recycle()
            }
        }
    }

}
