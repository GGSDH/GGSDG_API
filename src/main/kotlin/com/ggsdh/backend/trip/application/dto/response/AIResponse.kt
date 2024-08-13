package com.ggsdh.backend.trip.application.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatCompletionResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val choices: List<Choice>,
    val usage: Usage,
    @JsonProperty("system_fingerprint") val systemFingerprint: String,
)

data class Choice(
    val index: Int,
    val message: Message,
    @JsonProperty("logprobs") val logProbs: Any?, // Assuming logprobs is optional or can be any type
    @JsonProperty("finish_reason") val finishReason: String,
)

data class Message(
    val role: String,
    val content: String, // This will be JSON string, which we'll parse separately
    val refusal: Any?, // Assuming refusal can be null
)

data class Usage(
    @JsonProperty("prompt_tokens") val promptTokens: Int,
    @JsonProperty("completion_tokens") val completionTokens: Int,
    @JsonProperty("total_tokens") val totalTokens: Int,
)

data class ParsedContent(
    val title: String,
    val description: String,
    val days: List<DayPlan>,
)

data class DayPlan(
    val day: Int,
    val tripAreaNames: List<String>,
)
