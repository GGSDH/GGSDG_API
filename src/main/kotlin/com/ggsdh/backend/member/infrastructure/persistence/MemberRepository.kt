package com.ggsdh.backend.member.infrastructure.persistence

import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByMemberIdentification(memberIdentification: MemberIdentification): Member?
}
