package jp.glory.practice.spring.runner.process

class ProcessParameter(
    val batchName: String,
    private val values: Map<String, List<String>>
) {
    fun getParamValues(key: String): List<String> =
        values[key] ?: emptyList()

    fun getParamValue(key: String): String {
        val values = getParamValues(key)
        return if (values.isEmpty()) {
            return ""
        } else {
            values.first()
        }
    }
}