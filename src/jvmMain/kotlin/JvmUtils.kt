import java.io.File

actual fun getMillis() = System.currentTimeMillis()

actual fun readAllText(filePath: String) = File(filePath).readLines()