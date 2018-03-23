package soutvoid.com.personalwallet.util

/**
 * Created by andrew on 23.03.18.
 */
infix fun Long.floorTo(base: Long): Long =
        (Math.floor(this / base.toDouble()) * base).toLong()