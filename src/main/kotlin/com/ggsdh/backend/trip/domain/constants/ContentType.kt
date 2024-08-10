package com.ggsdh.backend.trip.domain.constants

enum class ContentType(
        val value: String
) {
    //contentTypeId => 테이블 나누는 Key

    TOURISM_SPOT("관광지"),
    FESTIVAL_EVENT("행사"),
    RESTAURANT("음식점")
}
