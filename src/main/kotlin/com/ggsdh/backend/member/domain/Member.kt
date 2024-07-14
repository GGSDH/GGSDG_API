package com.ggsdh.backend.member.domain

import com.ggsdh.backend.auth.domain.constants.Role
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "t_member")
class Member(
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long?,
    @Enumerated(EnumType.STRING) val role: Role,
    @OneToOne(
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true,
    ) val memberIdentification: MemberIdentification,
) : BaseEntity()
