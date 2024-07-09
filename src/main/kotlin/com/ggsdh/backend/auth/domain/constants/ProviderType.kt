package com.ggsdh.backend.auth.domain.constants

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.security.exception.AuthError

enum class ProviderType(val provider: String) {
    KAKAO("kakao"),
    APPLE("apple"),
    ;

    companion object {
        fun from(provider: String): ProviderType {
            return entries.firstOrNull { it.provider == provider }
                ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
        }
    }

    fun isKakao(): Boolean {
        return this == KAKAO
    }

    fun isApple(): Boolean {
        return this == APPLE
    }
}
