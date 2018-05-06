package soutvoid.com.personalwallet.ui.screen.addgoal

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import butterknife.BindView
import butterknife.OnItemSelected
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import org.jetbrains.anko.support.v4.withArguments
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.app.App
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.base.BaseFragment
import soutvoid.com.personalwallet.ui.common.Saveable
import soutvoid.com.personalwallet.ui.common.widget.ChooseDateSectionView
import soutvoid.com.personalwallet.ui.common.widget.InputNameSectionView
import soutvoid.com.personalwallet.ui.common.widget.InputValueSectionView
import soutvoid.com.personalwallet.ui.screen.addgoal.data.GoalData
import soutvoid.com.personalwallet.ui.screen.addgoal.data.ReminderPeriod
import soutvoid.com.personalwallet.ui.util.delegates.argument
import soutvoid.com.personalwallet.util.GOAL_ID

class AddGoalFragment : BaseFragment(), AddGoalView, Saveable {

    companion object {
        fun newInstance(goalLocalId: Long?): AddGoalFragment =
                AddGoalFragment().withArguments(GOAL_ID to goalLocalId)
    }

    private val goalLocalId by argument<Long>(GOAL_ID)

    @BindView(R.id.add_goal_target_value)
    lateinit var targetValueInput: InputValueSectionView
    @BindView(R.id.add_goal_date)
    lateinit var dateInput: ChooseDateSectionView
    @BindView(R.id.add_goal_name)
    lateinit var nameInput: InputNameSectionView
    @BindView(R.id.add_goal_reminder_value)
    lateinit var reminderValueInput: EditText
    @BindView(R.id.add_goal_reminer_period)
    lateinit var reminderPeriodSpinner: Spinner
    private lateinit var reminderPeriodAdapter: ArrayAdapter<String>

    private var selectedReminderPeriodIndex: Int = 0

    @InjectPresenter(type = PresenterType.WEAK)
    lateinit var presenter: AddGoalPresenter

    @ProvidePresenter(type = PresenterType.WEAK)
    fun providePresenter(): AddGoalPresenter = AddGoalPresenter(App.instance.kodein, goalLocalId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun getLayoutResId(): Int = R.layout.fragment_add_goal

    override fun save() {
        presenter.save(GoalData(
                targetValueInput.valueText,
                dateInput.calendar.timeInMillis,
                nameInput.text,
                reminderValueInput.text.toString(),
                ReminderPeriod.values()[selectedReminderPeriodIndex]
        ))
    }

    override fun fillFieldsForEdit(goal: Goal) {
        targetValueInput.text = goal.targetMoneyValue.toString()
        dateInput.setDateAndTime(goal.period)
        nameInput.text = goal.name
        val reminderPeriod = ReminderPeriod.findBySeconds(goal.reminedIntervalSec)
        reminderValueInput.setText((goal.reminedIntervalSec / reminderPeriod.valueSeconds).toString())
        selectedReminderPeriodIndex = ReminderPeriod.values().indexOfFirst { it == reminderPeriod }
        reminderPeriodSpinner.setSelection(selectedReminderPeriodIndex)
    }

    override fun finish() {
        activity?.finish()
    }

    override fun showInvalidReminderValueError() {
        reminderValueInput.error = getString(R.string.add_goal_invalid_reminder_value_error)
    }

    override fun showInvalidTargetValueError() {
        targetValueInput.valueInput.error = getString(R.string.add_goal_invalid_target_value_error)
        targetValueInput.valueInput.isErrorEnabled = true
    }

    private fun initViews() {
        context?.let {
            reminderPeriodAdapter = ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item,
                    ReminderPeriod.values().map { period -> it.resources.getQuantityString(period.label, 1) })
            reminderPeriodSpinner.adapter = reminderPeriodAdapter
        }
        dateInput.timeTv.visibility = View.GONE
        dateInput.datePrefix = getString(R.string.add_goal_date_prefix)
        reminderPeriodAdapter.setNotifyOnChange(false)
        RxTextView.textChangeEvents(reminderValueInput).doOnNext {
            reminderValueInput.error = null
            val value = reminderValueInput.text.toString().toIntOrNull()
            value?.let { setNewReminderPeriodLabels(it) }
        }.subscribe()
        targetValueInput.valueInput.editText?.let {
            RxTextView.textChangeEvents(it).doOnNext {
                targetValueInput.valueInput.isErrorEnabled = false
            }.subscribe()
        }
    }

    private fun setNewReminderPeriodLabels(quantity: Int) {
        context?.let {
            reminderPeriodAdapter.clear()
            reminderPeriodAdapter.addAll(ReminderPeriod.values()
                    .map { period -> it.resources.getQuantityString(period.label, quantity) })
            reminderPeriodAdapter.notifyDataSetChanged()
        }
    }

    @OnItemSelected(R.id.add_goal_reminer_period)
    fun onReminderPeriodSelected(parent: AdapterView<*>, itemSelected: View, index: Int, selectedId: Long) {
        selectedReminderPeriodIndex = index
    }
}