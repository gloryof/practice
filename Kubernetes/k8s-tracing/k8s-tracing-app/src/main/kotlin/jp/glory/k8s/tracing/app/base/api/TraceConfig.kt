package jp.glory.k8s.tracing.app.base.api

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("custom.trace")
class TraceConfig(
    var nodeName: String = "",
    var nameSpace: String = "",
    var podName: String = "",
    var podIp: String = ""
)