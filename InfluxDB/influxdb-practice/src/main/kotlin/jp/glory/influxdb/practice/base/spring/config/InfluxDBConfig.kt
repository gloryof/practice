package jp.glory.influxdb.practice.base.spring.config

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InfluxDBConfig {
    @Bean
    fun InfluxDBClient(property: InfluxDBProperty): InfluxDBClient =
        InfluxDBClientFactory.create(
            property.url,
            property.token.toCharArray(),
            property.org,
            property.bucket
        )

}

@ConfigurationProperties(prefix = "app.store.influxdb")
data class InfluxDBProperty @ConstructorBinding constructor(
    val url: String,
    val token: String,
    val org: String,
    val bucket: String
)