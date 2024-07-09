package com.ggsdh.backend.auth.infrastructure.kakao

import com.ggsdh.backend.auth.domain.OauthUriGenerator
import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.infrastructure.kakao.model.KakaoProperties
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

@Component
class KakaoUriGenerator(
    private val kakaoProperties: KakaoProperties,
) : OauthUriGenerator {
    override fun isSupported(provider: ProviderType): Boolean {
        return provider.isKakao()
    }

    override fun generate(): URI {
        return UriComponentsBuilder
            .fromUriString(kakaoProperties.authorizationEndpoint)
            .queryParam("response_type", "code")
            .queryParam("client_id", kakaoProperties.clientId)
            .queryParam("redirect_uri", kakaoProperties.redirectUri)
            .build()
            .toUri()
    }
}
