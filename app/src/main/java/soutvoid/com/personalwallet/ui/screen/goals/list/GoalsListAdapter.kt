package soutvoid.com.personalwallet.ui.screen.goals.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.domain.goal.Goal
import soutvoid.com.personalwallet.ui.util.inflate

class GoalsListAdapter(var goals: MutableList<Goal> = mutableListOf(),
                       var onItemClickListener: ((Int) -> Unit)? = null)
    : RecyclerView.Adapter<GoalsListAdapter.GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = parent.inflate(R.layout.item_goal)
        return GoalViewHolder(view)
    }

    override fun getItemCount(): Int = goals.size

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(goals[position])
    }

    inner class GoalViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.item_goal_name)
        lateinit var nameTv: TextView

        init {
            ButterKnife.bind(this, view)
            view.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
        }

        fun bind(goal: Goal) {
            with(goal) {
                nameTv.text = name
            }
        }

    }

}