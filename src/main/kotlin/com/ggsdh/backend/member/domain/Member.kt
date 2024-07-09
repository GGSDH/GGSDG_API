package com.ggsdh.backend.member.domain

import com.ggsdh.backend.auth.domain.constants.ProviderType
import com.ggsdh.backend.auth.domain.constants.Role
import com.ggsdh.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "t_member")
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long?,
    val providerId: Long,
    @Enumerated(EnumType.STRING) val providerType: ProviderType,
    @Enumerated(EnumType.STRING) val role: Role,
) : BaseEntity()
