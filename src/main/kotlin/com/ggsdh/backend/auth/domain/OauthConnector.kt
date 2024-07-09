package com.ggsdh.backend.auth.domain

import com.ggsdh.backend.auth.application.dto.response.OauthTokenResponse
import com.ggsdh.backend.auth.application.dto.response.ResourceIdResponse
import com.ggsdh.backend.auth.domain.constants.ProviderType

interface OauthConnector {
    fun isSupported(provider: ProviderType): Boolean

    fun fetchOauthToken(code: String): OauthTokenResponse

    fun fetchMemberInfo(accessToken: String): ResourceIdResponse
}
