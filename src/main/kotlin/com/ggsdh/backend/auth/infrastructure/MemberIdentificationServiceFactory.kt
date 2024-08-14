package com.ggsdh.backend.auth.infrastructure

import com.ggsdh.backend.auth.domain.MemberIdentificationService
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.apple.AppleUserIdentificationService
import com.ggsdh.backend.auth.infrastructure.kakao.KakaoMemberIdentificationService
import org.springframework.stereotype.Service

@Service
class MemberIdentificationServiceFactory(
    private val kakaoMemberIdentificationService: KakaoMemberIdentificationService,
    private val appleUserIdentificationService: AppleUserIdentificationService,
) {
    fun create(providerType: ProviderType): MemberIdentificationService =
        when (providerType) {
            ProviderType.KAKAO -> kakaoMemberIdentificationService
            ProviderType.APPLE -> appleUserIdentificationService
            ProviderType.DUMMY -> TODO()
        }
}
