package soutvoid.com.personalwallet.ui.screen.addentry.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputLayout
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import soutvoid.com.personalwallet.R

/**
 * Created by andrew on 18.03.18.
 */
class InputNameSectionView : ConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @BindView(R.id.name_input)
    lateinit var nameInput: TextInputLayout

    var text: String
        get() = nameInput.editText?.text?.toString() ?: ""
        set(value) {
            nameInput.editText?.setText(value)
        }

    init {
        View.inflate(context, R.layout.view_input_name_section, this)
        ButterKnife.bind(this, rootView)
    }
}