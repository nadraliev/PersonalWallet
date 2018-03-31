package soutvoid.com.personalwallet.ui.screen.main.data

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import soutvoid.com.personalwallet.R

/**
 * Created by andrew on 31.03.18.
 */
sealed class SectionData {
    companion object {
        fun getAllValues() =
                listOf<SectionData>(SectionCategories())
    }
}

data class SectionCategories(@DrawableRes val iconResId: Int = R.drawable.ic_tag_multiple_grey600_48dp,
                             @StringRes val nameResId: Int = R.string.main_section_categories_name) : SectionData()