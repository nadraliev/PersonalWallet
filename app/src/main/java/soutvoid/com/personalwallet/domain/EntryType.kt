package soutvoid.com.personalwallet.domain

import java.io.Serializable

/**
 * Created by andrew on 16.03.18.
 */
sealed class EntryType : Serializable {
    companion object {
        const val TYPE_INCOME_NAME = "income"
        const val TYPE_OUTCOME_NAME = "outcome"
        fun getAll() = listOf(Income, Outcome)
    }
}

object Income : EntryType(), Serializable
object Outcome : EntryType(), Serializable