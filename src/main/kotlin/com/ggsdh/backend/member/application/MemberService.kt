package com.ggsdh.backend.member.application

import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
)
