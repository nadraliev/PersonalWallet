package soutvoid.com.personalwallet.ui.screen.addentry.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.design.widget.TextInputLayout
import android.text.Selection
import android.util.AttributeSet
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding2.widget.RxTextView
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.util.getCurrencySymbol

/**
 * Created by andrew on 18.03.18.
 */
class InputValueSectionView : ConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @BindView(R.id.value_input)
    lateinit var valueInput: TextInputLayout

    private val currencySymbol = " ${getCurrencySymbol()}"

    var text: String
        get() = valueInput.editText?.text?.toString() ?: ""
        set(value) {
            valueInput.editText?.setText(value)
        }

    var valueText: String
        get() = text.substringBefore(currencySymbol)
        set(value) {
            text = value + currencySymbol
        }

    init {
        View.inflate(context, R.layout.view_input_value_section, this)
        ButterKnife.bind(this, rootView)
        valueInput.editText?.setText(currencySymbol)
        Selection.setSelection(valueInput.editText?.text, valueInput.editText
                ?.length()?.minus(currencySymbol.length)
                ?: 0)

        RxTextView.textChanges(valueInput.editText!!).doOnNext {
            if (!it.toString().endsWith(currencySymbol)) {
                var text = it.toString()
                text = text.replace(currencySymbol, "") + currencySymbol
                valueInput.editText?.setText(text)
                Selection.setSelection(valueInput.editText?.text, valueInput.editText
                        ?.length()?.minus(currencySymbol.length)
                        ?: 0)
            }
        }.subscribe()
    }

}