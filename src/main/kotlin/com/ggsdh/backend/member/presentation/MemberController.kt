package com.ggsdh.backend.member.presentation

import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.member.application.MemberService
import com.ggsdh.backend.member.application.dto.request.MemberRoleRequest
import com.ggsdh.backend.member.application.dto.response.MemberTokenResponse
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
@RequestMapping("/api/v1")
class MemberController(
        private val memberService: MemberService
) {
    @PostMapping("/dummy")
    fun createDummyMember(
            @RequestBody memberRoleRequest: MemberRoleRequest
    ): BaseResponse<MemberTokenResponse> {
        val createDummyMember = memberService.createDummyMember(memberRoleRequest)
        return BaseResponse.success(createDummyMember)
    }
}
