package soutvoid.com.personalwallet.ui.screen.history.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.screen.history.data.SectionData
import soutvoid.com.personalwallet.ui.util.inflate

/**
 * Created by andrew on 31.03.18.
 */
class SectionsListAdapter(var sections: List<SectionData> = listOf(),
                          var onItemClick: ((Int, View) -> Unit)? = null)
    : RecyclerView.Adapter<SectionsListAdapter.SectionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = parent.inflate(R.layout.item_section_main)
        return SectionViewHolder(view)
    }

    override fun getItemCount(): Int = sections.size

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(sections[position])
    }

    inner class SectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.item_section_icon)
        lateinit var iconIv: ImageView
        @BindView(R.id.item_section_label)
        lateinit var labelTv: TextView

        init {
            ButterKnife.bind(this, view)
            itemView.setOnClickListener { onItemClick?.invoke(adapterPosition, it) }
        }

        fun bind(sectionData: SectionData) {
            labelTv.setText(sectionData.nameResId)
            iconIv.setImageResource(sectionData.iconResId)
        }

    }

}