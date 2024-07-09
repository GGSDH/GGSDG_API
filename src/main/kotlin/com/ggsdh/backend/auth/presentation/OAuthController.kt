package com.ggsdh.backend.auth.presentation

import com.ggsdh.backend.auth.application.AuthService
import com.ggsdh.backend.auth.application.dto.response.AuthResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/oauth")
class OAuthController(private val authService: AuthService) {
    @GetMapping("/{provider}/login")
    fun login(
        @PathVariable provider: String,
        @RequestParam code: String,
    ): ResponseEntity<AuthResponse> {
        val loginResponse = authService.login(provider, code)
        return ResponseEntity.ok(loginResponse)
    }
}
