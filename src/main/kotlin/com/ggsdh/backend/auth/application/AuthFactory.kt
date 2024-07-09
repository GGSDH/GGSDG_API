package com.ggsdh.backend.auth.application

import com.ggsdh.backend.auth.domain.OauthConnector
import com.ggsdh.backend.auth.domain.OauthUriGenerator
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.security.exception.AuthError
import org.springframework.stereotype.Component

@Component
class AuthFactory(
    private val connectors: List<OauthConnector>,
    private val uriProviders: List<OauthUriGenerator>,
) {
    fun getAuthConnector(provider: String): OauthConnector {
        val providerType = ProviderType.from(provider)

        return connectors.firstOrNull {
            it.isSupported(providerType)
        } ?: throw BusinessException(AuthError.UNSUPPORTED_PROVIDER)
    }
}
