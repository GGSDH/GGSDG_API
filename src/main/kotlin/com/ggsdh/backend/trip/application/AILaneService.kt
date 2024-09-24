package com.ggsdh.backend.trip.application

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ggsdh.backend.trip.application.dto.request.AIRequest
import com.ggsdh.backend.trip.application.dto.request.AIRequestMessage
import com.ggsdh.backend.trip.application.dto.request.AITourArea
import com.ggsdh.backend.trip.application.dto.request.AIUserMessage
import com.ggsdh.backend.trip.application.dto.request.AIUserRequest
import com.ggsdh.backend.trip.application.dto.response.ChatCompletionResponse
import com.ggsdh.backend.trip.application.dto.response.ParsedContent
import com.ggsdh.backend.trip.domain.Lane
import com.ggsdh.backend.trip.domain.LaneMapping
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import com.ggsdh.backend.trip.infrastructure.LaneMappingRepository
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.presentation.dto.AILaneResponse
import com.ggsdh.backend.trip.presentation.dto.AIResponseDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.math.pow
import kotlin.math.sqrt

data class Position(val latitude: Double, val longitude: Double)


@Service
class AILaneService(
    private val objectMapper: ObjectMapper,
    private val tourAreaService: TourAreaService,
    private val laneMappingRepository: LaneMappingRepository,
    private val laneRepository: LaneRepository,
    @Value("\${openai.api.key}")
    private val openaiApiKey: String,
    private val laneService: LaneService,
) {
    val logger = LoggerFactory.getLogger(this::class.java)
    // Function to compute the distance between two positions using the Haversine formula
    fun distance(p1: Position, p2: Position): Double {
        val R = 6371e3 // Earth's radius in meters
        val phi1 = Math.toRadians(p1.latitude)
        val phi2 = Math.toRadians(p2.latitude)
        val deltaPhi = Math.toRadians(p2.latitude - p1.latitude)
        val deltaLambda = Math.toRadians(p2.longitude - p1.longitude)

        val a = Math.sin(deltaPhi / 2).pow(2.0) +
                Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(deltaLambda / 2).pow(2.0)
        val c = 2 * Math.atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }

    // Function to build a route by ordering laneMappings based on proximity
    fun buildRoute(laneMappings: List<LaneMapping>): List<LaneMapping> {
        if (laneMappings.isEmpty()) return emptyList()

        val remaining = laneMappings.toMutableList()
        val route = mutableListOf<LaneMapping>()

        // Start from the first laneMapping
        var current = remaining.removeAt(0)
        route.add(current)

        while (remaining.isNotEmpty()) {
            val currentPosition = Position(
                current.tourArea?.latitude ?: 0.0,
                current.tourArea?.longitude ?: 0.0
            )

            // Find the closest unvisited laneMapping
            val next = remaining.minByOrNull { lm ->
                val position = Position(
                    lm.tourArea?.latitude ?: 0.0,
                    lm.tourArea?.longitude ?: 0.0
                )
                distance(currentPosition, position)
            } ?: break

            remaining.remove(next)
            route.add(next)
            current = next
        }

        // Update the sequence numbers based on the new order
        route.forEachIndexed { index, laneMapping ->
            laneMapping.sequence = (index + 1).toLong()
        }

        return route
    }

    fun generateAILane(
        id: Long,
        request: AIUserRequest,
    ): AIResponseDto {
        val restTemplate = RestTemplate()

        val tourAreas =

            tourAreaService
                .getAllBySigunguCodes(
                    request.sigunguCode,
                ).filter {
                    it.tripThemeConstants in request.tripThemeConstants
                }

        val aiTourData =
            tourAreas.map {
                AITourArea(
                    it.id!!,
                    it.name,
                    it.latitude,
                    it.longitude,
                    it.ranking?.toInt(),
                    it.likes.toInt(),
                    it.sigunguCode,
                    it.tripThemeConstants,
                )
            }

        val aiUserMessage =
            AIUserMessage(
                request.days,
                aiTourData,
            )
        val aiRequest =
            AIRequest(
                "gpt-4o-mini",
                0.0,
                listOf(
                    AIRequestMessage(
                        "system",
                        "당신은 월 1억 원을 버는 경기사당행 서비스의 최고 여행 기획자입니다. 고객이 요청한 여행 날짜에 맞춰 완벽하게 여행 일정을 짜는 것이 목표입니다. 고객이 제공하는 데이터는 JSON 형식으로 제공되며, 'days'에는 고객이 원하는 여행 일수가 포함되고, 'data'에는 경기사당행에서 제공하는 여행지 데이터베이스가 포함됩니다. 이 정보를 바탕으로 고객이 쉽고, 편안하며, 즐거운 여행을 할 수 있도록 일정을 구성해야 합니다. 최종 결과물에는 재미있고 위트 있는 여행 일정 제목이 포함되어야 하며, 여행 일정은 각 날짜별로 'days' 배열에 여행지 이름을 포함하는 형식으로 반환되어야 합니다. 여행지 설명으로는 이 여행을 계획한 이유, 각 여행일에 대한 간략한 여행 일정 (1일당 1줄의 설명. 1일차는 이걸 하고요, 2일차는 이걸 합니다. 이렇게 .) , 그리고 주의 사항에 대해 10줄 길게. 적어줘. 길게 안적으면 1줄당 100만원씩 월급 삭감. 최대한 상세하게 설명해. 각 여행 일 마다 여행 컨셉과 간력학 일정을 소개해라.  중요 조건: 하루에 최소 5개 이상의 목적지를 추천해야 하며, ****이 중 식당은 1개만 포함될 수 있습니다 ****. 그리고 축제는 여행지에서 제외해. 예를 들어 고객이 5일 여행을 요청했다면, 'days'에는 5개의 여행 일정 세트가 포함되어야 합니다. 반환하는 여행지 이름은 반드시 제공된 실제 데이터 내에 존재해야 하며, 그렇지 않으면 월급을 받지 못합니다. 중요: 하루에 최소 7개의 목적지를 추천해야 하며, 예외는 없습니다. ***중요*** 너의 최고 책임자인 경기사당행의 PM 마소영님의 특별 요청사항이 있어. 2일차 이상 추천 받을 시 중복 데이터가 많이 발생한다. 따라서, 2일 이상 추천을 제공할 때 이미 추천한 여행지는 절대 추천하지 않도록 해야한다. 그렇지 않으면 월급 500만원을 벌금으로 부과할것 이야. 그리고 제목과 설명은 ***무조건 한국어로 작성*** 해야한다.",
                    ),
                    AIRequestMessage("user", objectMapper.writeValueAsString(aiUserMessage)),
                ),
                objectMapper.readValue<Map<String, Any>>(
                    """
                                {
                        "type": "json_schema",
                        "json_schema": {
                            "name": "tripresponse",
                            "strict": true,
                            "schema": {
                                "type": "object",
                                "properties": {
                                    "title": {
                                        "type": "string"
                                    }, "description": {
                                        "type": "string"
                                    },
                                    "days": {
                                        "type": "array",
                                        "items": {
                                            "type": "object",
                                            "properties": {
                                                "day": {
                                                    "type": "integer"
                                                },
                                                "tripAreaNames": {
                                                    "type": "array",
                                                    "items": {
                                                        "type": "string"
                                                    }
                                                }
                                            },
                                            "required": [
                                                "day",
                                                "tripAreaNames"
                                            ],
                                            "additionalProperties": false
                                        }
                                    }
                                },
                                "required": [
                                    "title",
                                    "description",
                                    "days"
                                ],
                                "additionalProperties": false
                            }
                        }
                    }
                    """.trimIndent(),
                ),
            )

        val entity =
            HttpEntity<AIRequest>(
                aiRequest,
                HttpHeaders().apply {
                    set(
                        "Authorization",
                        "Bearer $openaiApiKey", // TODO,
                    )
                },
            )

        val response: ResponseEntity<ChatCompletionResponse> =
            restTemplate.exchange(
                "https://api.openai.com/v1/chat/completions",
                HttpMethod.POST,
                entity,
                ChatCompletionResponse::class.java,
            )
        val data =
            response.body
                ?.choices
                ?.first()
                ?.message
                ?.content ?: throw Exception("No content")

        logger.info(data)
        val parsedContent =
            objectMapper.readValue(
                data,
                ParsedContent::class.java,
            )



        val lane =
            Lane(
                parsedContent.title,
                request.tripThemeConstants.firstOrNull() ?: TripThemeConstants.CULTURE,
                0,
                true,
                null,
                request.days,
                parsedContent.description
            )



        // Your original code to build laneMappings
        val laneMappings = parsedContent.days
            .flatMap { dayPlan ->
                dayPlan.tripAreaNames
                    .map { it to dayPlan.day }
                    .mapIndexedNotNull { index, dayPlan ->
                        val laneMapping = LaneMapping()
                        laneMapping.sequence = (index + 1).toLong()
                        laneMapping.lane = lane
                        laneMapping.tourArea = tourAreas.firstOrNull { it.name == dayPlan.first }
                        laneMapping.day = dayPlan.second
                        if (laneMapping.tourArea == null) {
                            return@mapIndexedNotNull null
                        }
                        laneMapping
                    }
            }

        val rebuiltRoute = buildRoute(laneMappings)



        val saved = laneRepository.save(lane)
        laneMappingRepository.saveAll(rebuiltRoute)

        val laneResponse = laneService.getSpecificLaneResponse(id, saved.id!!)

        return AIResponseDto(
            saved.id!!,
            saved.name,
            saved.description ?: "",
            rebuiltRoute.groupBy { it.day }.map {
                AILaneResponse(
                    it.key.toInt(),
                    it.value.map { it.tourArea!!.name },
                    laneResponse.filter { lane ->
                        lane.day == it.key.toInt()
                    }
                )
            },
            request.sigunguCode,
        )
    }
}
