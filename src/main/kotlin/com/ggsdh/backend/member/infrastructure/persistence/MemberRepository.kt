package com.ggsdh.backend.member.infrastructure.persistence

import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByMemberIdentification(memberIdentification: MemberIdentification): Member?
}
