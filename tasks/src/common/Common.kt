package common

import java.io.File

class Common {
    companion object {
        fun getNumbers(filePath: String): List<Int> {
            return File(filePath).readLines().map { it.toInt() }
        }

        fun getLines(filePath: String): List<String> {
            return File(filePath).readLines()
        }

        fun getAllText(filePath: String): String {
            return File(filePath).readText()
        }
    }
}