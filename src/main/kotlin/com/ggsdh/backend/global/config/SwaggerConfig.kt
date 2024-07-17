package com.ggsdh.backend.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("swagger 테스트")
            .version("1.0")
            .description("API에 대한 설명 부분")
        return OpenAPI()
            .components(Components())
            .info(info)
    }
}
