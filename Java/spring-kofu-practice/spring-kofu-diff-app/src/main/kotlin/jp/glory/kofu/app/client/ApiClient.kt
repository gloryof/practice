package jp.glory.kofu.app.client

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.postForEntity

@Component
class ApiClient(
    val config: ApiClientConfig,
    val restTemplate: RestTemplate
) {
    fun registerPerson(request: ExternalPersonRequest): String {
        val url = "${config.targetUrl}/person"
        val response = restTemplate.postForEntity<String>(url, request)
        val result = when(response.statusCode) {
            HttpStatus.OK -> response.body
            else -> null
        }

        return result ?: throw RuntimeException("Register external person is failed")
    }

    fun getPerson(id: String): ExternalPersonResponse {
        val url = "${config.targetUrl}/person/$id"
        val response = restTemplate.getForEntity<ExternalPersonResponse>(url)
        val result = when(response.statusCode) {
            HttpStatus.OK -> response.body
            else -> null
        }

        return result ?: throw RuntimeException("Register external person is failed")
    }
}

data class ExternalPersonRequest(
    var name: String? = null,
    var age: Int? = null
)


data class ExternalPersonResponse(
    val id: String,
    val name: String ,
    val age: Int
)