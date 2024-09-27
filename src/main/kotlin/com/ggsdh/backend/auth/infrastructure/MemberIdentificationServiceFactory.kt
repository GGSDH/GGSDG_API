package com.ggsdh.backend.auth.infrastructure

import com.ggsdh.backend.auth.domain.MemberIdentificationService
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.apple.AppleUserIdentificationService
import com.ggsdh.backend.auth.infrastructure.kakao.PasswordMemberIdentificationService
import org.springframework.stereotype.Service

@Service
class MemberIdentificationServiceFactory(
    private val kakaoMemberIdentificationService: PasswordMemberIdentificationService,
    private val appleUserIdentificationService: AppleUserIdentificationService,
    private val passwordMemberIdentificationService: PasswordMemberIdentificationService,
) {
    fun create(providerType: ProviderType): MemberIdentificationService =
        when (providerType) {
            ProviderType.KAKAO -> kakaoMemberIdentificationService
            ProviderType.APPLE -> appleUserIdentificationService
            ProviderType.PASSWORD -> passwordMemberIdentificationService
            ProviderType.DUMMY -> TODO()
        }
}
