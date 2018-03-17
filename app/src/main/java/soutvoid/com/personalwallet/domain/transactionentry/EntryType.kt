package soutvoid.com.personalwallet.domain.transactionentry

import java.io.Serializable

/**
 * Created by andrew on 16.03.18.
 */
sealed class EntryType : Serializable {
    companion object {
        const val TYPE_INCOME_NAME = "income"
        const val TYPE_OUTCOME_NAME = "outcome"
        fun getAll() = listOf(Income, Outcome)
        fun getByName(name: String): EntryType? = when (name.toLowerCase()) {
            TYPE_INCOME_NAME -> Income
            TYPE_OUTCOME_NAME -> Outcome
            else -> null
        }
    }

    fun getName(): String = when (this) {
        is Income -> TYPE_INCOME_NAME
        is Outcome -> TYPE_OUTCOME_NAME
    }

    override fun toString(): String = getName()
}

object Income : EntryType(), Serializable
object Outcome : EntryType(), Serializable