package com.ggsdh.backend.auth.infrastructure.kakao

import com.ggsdh.backend.auth.domain.MemberIdentificationService
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.auth.infrastructure.MemberIdentificationJpaRepository
import com.ggsdh.backend.auth.infrastructure.kakao.dto.KakaoGetMeResponse
import com.ggsdh.backend.auth.infrastructure.kakao.dto.KakaoRefreshResponse
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.GlobalError
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class PasswordMemberIdentificationService(
    private val memberIdentificationJpaRepository: MemberIdentificationJpaRepository
) : MemberIdentificationService {
    override fun getToken(memberIdentification: MemberIdentification): String {
        return ""
    }

    override fun refreshIdentification(memberIdentification: MemberIdentification): MemberIdentification {
        return memberIdentification
    }

    override fun toMemberIdentification(
        tokenString: String, //id
        refreshToken: String?, //password
    ): MemberIdentification {
        val memberIdentification = memberIdentificationJpaRepository.findBySub(tokenString)
        if (memberIdentification == null) {
            val hash = BCrypt.hashpw(refreshToken, BCrypt.gensalt())

            println(
                "id: $tokenString, refreshToken: $refreshToken, hash: $hash"
            )
            return MemberIdentification(
                id = null,
                type = ProviderType.PASSWORD,
                kakaoId = null,
                sub = tokenString,
                refreshToken = hash,
                member = null,
            )
        }

        val isValidPassword =  BCrypt.checkpw(refreshToken, memberIdentification.refreshToken)

        if (!isValidPassword) {
            throw BusinessException(GlobalError.PASSWORD_NOT_MATCH)
        }

        return MemberIdentification(
            id = null,
            type = ProviderType.PASSWORD,
            kakaoId = null,
            sub = tokenString,
            refreshToken = memberIdentification.refreshToken,
            member = null,
        )
    }

    override fun revoke(userIdentification: MemberIdentification): Boolean {
       return true;
    }
}
