package jp.glory.practice.boot.app.test.tool

import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.json.JsonMapper

object JsonMapperBuilderCreator {
    fun create(): JsonMapper.Builder =
        JsonMapper.builder()
            .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
}
