package soutvoid.com.personalwallet.ui.screen.checks.list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import soutvoid.com.personalwallet.R
import soutvoid.com.personalwallet.ui.util.CHECK_DATE_FORMAT
import soutvoid.com.personalwallet.ui.util.inflate
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ChecksListAdapter(var photos: MutableList<File> = mutableListOf(),
                        var onItemClick: ((Int) -> Unit)? = null)
    : RecyclerView.Adapter<ChecksListAdapter.CheckViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckViewHolder {
        val view = parent.inflate(R.layout.item_check)
        return CheckViewHolder(view)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: CheckViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class CheckViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.item_check_image)
        lateinit var imageIv: ImageView
        @BindView(R.id.item_check_date)
        lateinit var dateTv: TextView

        init {
            ButterKnife.bind(this, view)
            view.setOnClickListener { onItemClick?.invoke(adapterPosition) }
        }

        fun bind(file: File) {
            Glide.with(itemView.context)
                    .load(file)
                    .into(imageIv)
            val dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
            dateTv.text = dateFormat.format(findCreatedDate(file))
        }

        private fun findCreatedDate(file: File): Date {
            val stringTokenizer = StringTokenizer(file.nameWithoutExtension, "_")
            return if (stringTokenizer.countTokens() > 1) {
                stringTokenizer.nextToken()     //skip 'check'
                val dateStr = "${stringTokenizer.nextToken()}_${stringTokenizer.nextToken()}"
                val dateParsingFormat = SimpleDateFormat(CHECK_DATE_FORMAT, Locale.getDefault())
                dateParsingFormat.parse(dateStr)
            } else {
                Date(file.lastModified())
            }
        }

    }

}