package soutvoid.com.personalwallet.ui.common.widget

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Parcelable
import android.support.constraint.ConstraintLayout
import android.text.format.DateFormat
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.tinsuke.icekick.extension.freezeInstanceState
import com.tinsuke.icekick.extension.serialState
import com.tinsuke.icekick.extension.unfreezeInstanceState
import soutvoid.com.personalwallet.R
import java.text.DateFormat.SHORT
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by andrew on 18.03.18.
 */
class ChooseDateSectionView : ConstraintLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @BindView(R.id.add_entry_date_tv)
    lateinit var dateTv: TextView
    @BindView(R.id.add_entry_time_tv)
    lateinit var timeTv: TextView

    var calendar: Calendar by serialState(Calendar.getInstance())

    var datePrefix: String = ""
        set(value) {
            field = value
            updateDateAndTime(calendar.timeInMillis)
        }

    init {
        View.inflate(context, R.layout.view_choose_date_section, this)
        ButterKnife.bind(this, rootView)
        updateDateAndTime(calendar.timeInMillis)
    }

    @OnClick(R.id.add_entry_date_tv)
    internal fun onDateClick(v: View) {
        showDatePicker()
    }

    @OnClick(R.id.add_entry_time_tv)
    internal fun onTimeClick(v: View) {
        showTimePicker()
    }

    fun setDateAndTime(seconds: Long) {
        calendar.timeInMillis = seconds * 1000
        updateDateAndTime(calendar.timeInMillis)
    }

    private fun showDatePicker() {
        DatePickerDialog(rootView.context, onDateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show()
    }

    private fun showTimePicker() {
        TimePickerDialog(rootView.context, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(rootView.context))
                .show()
    }

    private val onDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                updateDateAndTime(calendar.timeInMillis)
            }

    private val onTimeSetListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                updateDateAndTime(calendar.timeInMillis)
            }

    private fun updateDateAndTime(millis: Long) {
        val dateFormat = SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat.getTimeInstance(SHORT, Locale.getDefault())
        dateTv.text = "$datePrefix${dateFormat.format(Date(millis))}"
        timeTv.text = timeFormat.format(Date(millis))
    }

    override fun onSaveInstanceState(): Parcelable? =
            freezeInstanceState(super.onSaveInstanceState())

    override fun onRestoreInstanceState(state: Parcelable) {
        super.onRestoreInstanceState(unfreezeInstanceState(state))
        updateDateAndTime(calendar.timeInMillis)
    }
}