package com.ggsdh.backend.member.infrastructure.persistence

import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByProviderIdAndProviderType(
        providerId: Long,
        providerType: ProviderType,
    ): Member?
}
