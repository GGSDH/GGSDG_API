package com.ggsdh.backend.auth.infrastructure

import org.springframework.data.jpa.repository.JpaRepository

interface MemberIdentificationJpaRepository : JpaRepository<MemberIdentification, Long> {
    fun findByKakaoId(kakaoId: Long): MemberIdentification?

    fun findBySub(sub: String): MemberIdentification?
}
