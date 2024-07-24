package com.ggsdh.backend.auth.domain.constants

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.security.exception.AuthError

enum class Role {
    ROLE_GUEST,
    ROLE_TEMP_USER,
    ROLE_USER;

    companion object {
        fun from(role: String): Role {
            return entries.firstOrNull { it.name == role }
                    ?: throw BusinessException(AuthError.UNSUPPORTED_ROLE)
        }
    }

}
