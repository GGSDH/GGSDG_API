package com.ggsdh.backend.photobook.infra

import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.Dsl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AsyncHttpClientConfiguration {
    @Bean
    fun asyncHttpClient(): AsyncHttpClient = Dsl.asyncHttpClient()
}
