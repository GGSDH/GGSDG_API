package com.ggsdh.backend.auth.application.dto.request

import com.ggsdh.backend.auth.domain.constants.ProviderType

class LoginRequest(
    val providerType: ProviderType,
    val accessToken: String,
    val refreshToken: String?,
)
