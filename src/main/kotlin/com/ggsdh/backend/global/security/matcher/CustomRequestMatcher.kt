package com.ggsdh.backend.global.security.matcher

import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.stereotype.Component

@Component
class CustomRequestMatcher {
    fun authEndpoints(): RequestMatcher =
        OrRequestMatcher(
            AntPathRequestMatcher("/"), // Actuator Health Checker
            AntPathRequestMatcher("/api/v1/dummy"),
            AntPathRequestMatcher("/api/v1/trip/onboarding/themes"),
            AntPathRequestMatcher("/api/v1/oauth/**/**"), // Oauth Login
            AntPathRequestMatcher("/api/v1/actuator"),
            AntPathRequestMatcher("/api/v1/member/**/**"),
            AntPathRequestMatcher("/api/v1/restaurant"),
            AntPathRequestMatcher("/api/v1/ranking"),
            AntPathRequestMatcher("/api/v1/lane"),
        )

    fun tempUserEndpoints(): RequestMatcher =
        OrRequestMatcher(
            AntPathRequestMatcher("/api/v1/trip/onboarding"),
            AntPathRequestMatcher("/api/v1/member/signup"),
            AntPathRequestMatcher("/api/v1/member/nickname"),
            AntPathRequestMatcher("/api/v1/member/"),
            AntPathRequestMatcher("/api/v1/photobook"),
            AntPathRequestMatcher("/api/v1/photobook/**"),
        )

    fun userEndpoints(): RequestMatcher =
        OrRequestMatcher(
            AntPathRequestMatcher("/api/v1/search/**"),
        )
}
