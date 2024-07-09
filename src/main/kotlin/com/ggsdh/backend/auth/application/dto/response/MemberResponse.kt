package com.ggsdh.backend.auth.application.dto.response

import com.ggsdh.backend.auth.domain.constants.Role

data class MemberResponse(
    val memberId: Long,
    val role: Role,
)
