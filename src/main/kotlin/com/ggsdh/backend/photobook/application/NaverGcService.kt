package com.ggsdh.backend.photobook.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ggsdh.backend.photobook.application.dto.ApiResponse
import com.ggsdh.backend.photobook.application.dto.GetLocationInputDto
import com.ggsdh.backend.photobook.domain.Location
import org.asynchttpclient.AsyncHttpClient
import org.asynchttpclient.Dsl.asyncHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class NaverGcService(
    private val objectMapper: ObjectMapper,
    @Value("\${naver.gc.client-id}")
    private val clientId: String,
    @Value("\${naver.gc.client-secret}")
    private val clientSecret: String,
    private val asyncHttpClient: AsyncHttpClient,
) {
    fun getLocation(input: GetLocationInputDto): CompletableFuture<Location?> {
        if (input.lat == null || input.lon == null) {
            return CompletableFuture.completedFuture(null)
        }

        return asyncHttpClient
            .prepareGet(
                "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=${input.lon},${input.lat}&output=json&orders=roadaddr",
            ).setHeader("X-NCP-APIGW-API-KEY", clientSecret)
            .setHeader("X-NCP-APIGW-API-KEY-ID", clientId)
            .execute()
            .toCompletableFuture()
            .thenApply {
                val body = it.responseBody
                val response = objectMapper.readValue<ApiResponse>(body)

                if (response.status.code == 3) {
                    return@thenApply null
                }

                val buildingName =
                    response.results[0]
                        .land.addition0.value

                val city =
                    response.results[0]
                        .region.area1.name

                if (buildingName == "") {
                    val roadName =
                        response.results[0]
                            .land.name
                    return@thenApply Location(input.lat, input.lon, "$roadName 근처 어딘가", city)
                } else {
                    return@thenApply Location(input.lat, input.lon, buildingName, city)
                }
            }
    }
}
