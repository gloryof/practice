package jp.glory.practicegraphql.app.libs.graqphql

import graphql.scalars.datetime.DateScalar
import graphql.schema.idl.RuntimeWiring
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.stereotype.Component

@Component
class ScalarConfigurer : RuntimeWiringConfigurer {
    override fun configure(builder: RuntimeWiring.Builder) {
        builder.scalar(DateScalar.INSTANCE)
    }
}
