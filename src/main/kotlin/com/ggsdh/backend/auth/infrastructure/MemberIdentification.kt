package com.ggsdh.backend.auth.infrastructure

import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.member.domain.Member
import jakarta.persistence.*

@Entity
class MemberIdentification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column
    val type: ProviderType,
    @Column(unique = true)
    var kakaoId: Long?,
    @Column(unique = true)
    var sub: String?,
    @Column
    var refreshToken: String,
    @OneToOne(mappedBy = "memberIdentification")
    val member: Member?,
)
