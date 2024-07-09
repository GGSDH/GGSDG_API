package com.ggsdh.backend.auth.domain

import com.ggsdh.backend.auth.infrastructure.MemberIdentification

interface MemberIdentificationService {
    fun toMemberIdentification(
        tokenString: String,
        refreshToken: String?,
    ): MemberIdentification

    fun getToken(memberIdentification: MemberIdentification): String

    fun refreshIdentification(userIdentification: MemberIdentification): MemberIdentification

    fun revoke(userIdentification: MemberIdentification): Boolean
}
