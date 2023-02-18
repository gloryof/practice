package jp.glory.open_feature.practice.base.open_feature

import dev.openfeature.sdk.*

class OriginalFeatureProvider : FeatureProvider {
    private val values = mapOf(
        "practice.api.user.register" to true,
        "practice.api.product.register" to false,
    )
    override fun getMetadata(): Metadata {
        return Metadata { "Original Provider" }
    }

    override fun getBooleanEvaluation(
        key: String?,
        defaultValue: Boolean?,
        ctx: EvaluationContext?
    ): ProviderEvaluation<Boolean> {
        return ProviderEvaluation.builder<Boolean>()
            .value(values[key])
            .variant(key)
            .reason("")
            .build()
    }

    override fun getStringEvaluation(
        key: String?,
        defaultValue: String?,
        ctx: EvaluationContext?
    ): ProviderEvaluation<String> {
        throw UnsupportedOperationException("This method is not support")
    }

    override fun getIntegerEvaluation(
        key: String?,
        defaultValue: Int?,
        ctx: EvaluationContext?
    ): ProviderEvaluation<Int> {
        throw UnsupportedOperationException("This method is not support")
    }

    override fun getDoubleEvaluation(
        key: String?,
        defaultValue: Double?,
        ctx: EvaluationContext?
    ): ProviderEvaluation<Double> {
        throw UnsupportedOperationException("This method is not support")
    }

    override fun getObjectEvaluation(
        key: String?,
        defaultValue: Value?,
        ctx: EvaluationContext?
    ): ProviderEvaluation<Value> {
        throw UnsupportedOperationException("This method is not support")
    }
}