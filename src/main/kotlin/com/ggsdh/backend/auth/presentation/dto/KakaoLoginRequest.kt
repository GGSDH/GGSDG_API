package com.ggsdh.backend.auth.presentation.dto

class KakaoLoginRequest(
    val accessToken: String,
    val refreshToken: String?,
)
