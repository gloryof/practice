package jp.glory.practicegraphql.app.base.adaptor.web.config

import graphql.schema.idl.RuntimeWiring
import graphql.validation.rules.ValidationRules
import graphql.validation.schemawiring.ValidationSchemaWiring
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer

@Configuration
class GraqphQLRuntimeWiring : RuntimeWiringConfigurer {
    override fun configure(builder: RuntimeWiring.Builder) {
        ValidationRules.newValidationRules()
            .build()
            .let { ValidationSchemaWiring(it) }
            .also { builder.directiveWiring(it) }
    }
}
