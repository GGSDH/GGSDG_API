package com.ggsdh.backend.member.application.dto.request

import com.ggsdh.backend.auth.domain.constants.Role

data class MemberRoleRequest(
        val role: Role
)
