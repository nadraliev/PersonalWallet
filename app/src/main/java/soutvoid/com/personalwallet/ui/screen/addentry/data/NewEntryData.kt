package soutvoid.com.personalwallet.ui.screen.addentry.data

import soutvoid.com.personalwallet.domain.transactionentry.Category

/**
 * Created by andrew on 23.03.18.
 */
data class NewEntryData(val name: String, val category: Category?, val dateAndTimeMillis: Long,
                        val moneyValue: String)