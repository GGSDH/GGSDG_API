package com.ggsdh.backend.global.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

@Controller
class HomeController {
    @GetMapping("/")
    fun getHomePage(locale: Locale): String {
        return "home"
    }
}
