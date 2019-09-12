package co.uk.e2x.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@ConfigurationProperties(prefix = "myconfig")
class AppConfig {

    lateinit var baseUrl: String
    lateinit var apiKey: String

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }
}