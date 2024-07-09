package com.ggsdh.backend.global.view

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

@Controller
class HomeController {
    @GetMapping("/")
    fun getHomePage(locale: Locale): String {
        return "home"
    }

    @GetMapping("/api/v1/actuator")
    fun getHealthCheckResponse(): ResponseEntity<Unit> {
        return ResponseEntity.ok().build()
    }
}
