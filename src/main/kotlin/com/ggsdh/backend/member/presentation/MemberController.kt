package com.ggsdh.backend.member.presentation

import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import com.ggsdh.backend.global.dto.BaseResponse
import com.ggsdh.backend.global.security.annotation.AuthId
import com.ggsdh.backend.member.application.MemberService
import com.ggsdh.backend.member.application.dto.request.MemberRoleRequest
import com.ggsdh.backend.member.presentation.dto.MemberResponse
import com.ggsdh.backend.member.presentation.dto.NicknameChangeRequest
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
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
    @DeleteMapping("/member")
    fun withdraw(
        @AuthId id: Long,
    ): BaseResponse<Boolean> = BaseResponse.success(memberService.withdraw(id))

    @PutMapping("/member/nickname")
    fun updateNickname(
        @AuthId id: Long,
        @RequestBody nicknameChangeRequest: NicknameChangeRequest,
    ): BaseResponse<MemberResponse> {
        memberService.updateNickname(id, nicknameChangeRequest.nickname)

        val member = memberService.getMember(id)
        val themes = memberService.getTripThemeList(id)

        return BaseResponse.success(
            MemberResponse(
                member.id!!,
                member.nickname,
                member.memberIdentification.type.name,
                member.role.name,
                themes,
            ),
        )
    }

    @GetMapping("/member")
    fun getMember(
        @AuthId id: Long,
    ): BaseResponse<MemberResponse> {
        val member = memberService.getMember(id)
        val themes = memberService.getTripThemeList(id)

        return BaseResponse.success(
            MemberResponse(
                member.id!!,
                member.nickname,
                member.memberIdentification.type.name,
                member.role.name,
                themes,
            ),
        )
    }

    @PostMapping("/dummy")
    fun createDummyMember(
        @RequestBody memberRoleRequest: MemberRoleRequest,
    ): BaseResponse<AuthResponse> {
        val createDummyMember = memberService.createDummyMember(memberRoleRequest)
        return BaseResponse.success(
            AuthResponse.of(
                createDummyMember.accessToken,
                createDummyMember.member!!.role,
            ),
        )
    }
}
