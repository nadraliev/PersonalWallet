package soutvoid.com.personalwallet.ui.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

const val CHECK_DATE_FORMAT = "yyyyMMdd_HHmmss"

fun Context.createCheckImageFile(): File {
    val timeStamp = SimpleDateFormat(CHECK_DATE_FORMAT, Locale.getDefault()).format(Date())
    val imageFileName = "check_${timeStamp}_"
    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
    )
}

fun Context.getCheckImagesPaths(): List<File> =
        getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles()
                .filter { it.name.startsWith("check") }