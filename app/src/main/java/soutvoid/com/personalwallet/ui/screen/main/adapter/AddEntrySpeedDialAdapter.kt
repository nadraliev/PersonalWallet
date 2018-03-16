package soutvoid.com.personalwallet.ui.screen.main.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import soutvoid.com.personalwallet.domain.EntryType
import soutvoid.com.personalwallet.ui.util.getColor
import soutvoid.com.personalwallet.ui.util.getIcon
import soutvoid.com.personalwallet.ui.util.getName
import uk.co.markormesher.android_fab.SpeedDialMenuAdapter
import uk.co.markormesher.android_fab.SpeedDialMenuItem

/**
 * Created by andrew on 16.03.18.
 */
class AddEntrySpeedDialAdapter(private val context: Context,
                               private val onMenuItemClickListener: ((EntryType) -> Unit)? = null)
    : SpeedDialMenuAdapter() {

    companion object {
        private const val FAB_ROTATION_DEGREES = 225f
    }

    private val entryTypes = EntryType.getAll()

    override fun getCount(): Int = entryTypes.size

    override fun getMenuItem(context: Context, position: Int): SpeedDialMenuItem =
            with(entryTypes[position]) {
                SpeedDialMenuItem(context, getIcon(), getName())
            }

    override fun getBackgroundColour(position: Int): Int =
            ContextCompat.getColor(context, entryTypes[position].getColor())

    override fun onMenuItemClick(position: Int): Boolean {
        onMenuItemClickListener?.invoke(entryTypes[position])
        return true
    }

    override fun fabRotationDegrees(): Float = FAB_ROTATION_DEGREES
}