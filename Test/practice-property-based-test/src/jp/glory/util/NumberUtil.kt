package jp.glory.util

object NumberUtil {
    private val pattern = Regex("[-]*[0-9]+")
    fun isNumeric(value: String): Boolean = pattern.matches(value)
}