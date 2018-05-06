package soutvoid.com.personalwallet.util

/**
 * Created by andrew on 23.03.18.
 */
infix fun Long.floorTo(base: Long): Long =
        (Math.floor(this / base.toDouble()) * base).toLong()

infix fun List<*>.hasNoIndex(index: Int): Boolean = size <= index

fun <T1, T2, R> ifNotNull(first: T1?, second: T2?, action: (T1, T2) -> R): R? =
        if (first != null && second != null) action(first, second)
        else null

fun <T> MutableList<T>.replaceWith(newData: Iterable<T>) {
    clear()
    addAll(newData)
}