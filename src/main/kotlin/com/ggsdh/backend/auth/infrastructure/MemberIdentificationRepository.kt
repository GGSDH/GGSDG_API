package com.ggsdh.backend.auth.infrastructure

import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.member.domain.Member
import org.springframework.stereotype.Repository

@Repository
class MemberIdentificationRepository(
    private val memberIdentificationJpaRepository: MemberIdentificationJpaRepository,
) {
    fun findMemberByIdentification(memberIdentification: MemberIdentification): Member? =
        when (memberIdentification.type) {
            ProviderType.KAKAO ->
                memberIdentificationJpaRepository.findByKakaoId(
                    memberIdentification.kakaoId!!,
                )

            ProviderType.APPLE ->
                memberIdentificationJpaRepository.findBySub(
                    memberIdentification.sub!!,
                )

            ProviderType.PASSWORD ->
                memberIdentificationJpaRepository.findBySub(
                    memberIdentification.sub!!,
                )

            ProviderType.DUMMY -> TODO()
        }?.member
}
