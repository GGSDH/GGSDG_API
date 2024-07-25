package com.ggsdh.backend.member.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.member.application.MemberService
import com.ggsdh.backend.member.application.dto.request.MemberRoleRequest
import com.ggsdh.backend.member.application.dto.response.MemberTokenResponse
import com.ggsdh.backend.member.presentation.dto.NicknameChangeRequest
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1")
class MemberController(
    private val memberService: MemberService,
) {
    @PutMapping("/member/nickname")
    fun updateNickname(
        @AuthId id: Long,
        @RequestBody nicknameChangeRequest: NicknameChangeRequest,
    ) {
        memberService.updateNickname(id, nicknameChangeRequest.nickname)
    }

    @PostMapping("/dummy")
    fun createDummyMember(
        @RequestBody memberRoleRequest: MemberRoleRequest,
    ): BaseResponse<MemberTokenResponse> {
        val createDummyMember = memberService.createDummyMember(memberRoleRequest)
        return BaseResponse.success(createDummyMember)
    }
}
