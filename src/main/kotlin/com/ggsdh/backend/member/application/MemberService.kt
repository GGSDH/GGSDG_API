package com.ggsdh.backend.member.application

import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.member.exception.MemberError
import com.ggsdh.backend.member.infrastructure.persistence.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.*

@Component
@Transactional
class MemberService(
    private val memberRepository: MemberRepository,
) {
    fun findByMemberIdentification(memberIdentification: MemberIdentification) {
        memberRepository.findByMemberIdentification(memberIdentification)
    }
}
