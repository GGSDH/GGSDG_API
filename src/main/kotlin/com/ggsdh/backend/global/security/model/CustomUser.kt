package com.ggsdh.backend.global.security.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
        val id: Long?,
        username: String,
        password: String,
        authorities: Collection<GrantedAuthority>,
) : User(username, password, authorities)
