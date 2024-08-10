package com.ggsdh.backend.trip.domain.constants

enum class TripThemeConstants(
        val title: String,
        val icon: String,
) {
    // cat
    NATURAL("자연", "\uD83C\uDF3F"),
    CULTURE("문화", "\uD83C\uDFAD"),
    REPORTS("레포츠", "\uD83C\uDFC3\uD83C\uDFFB"),
    SHOPPING("쇼핑", "\uD83D\uDECD\uFE0F"),
    RESTAURANT("음식점", "\uD83C\uDF7D\uFE0F")
}
