package com.ggsdh.backend.member.domain

import com.ggsdh.backend.auth.domain.constants.Role
import com.ggsdh.backend.auth.infrastructure.MemberIdentification
import com.ggsdh.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "t_member")
class Member(
    @Enumerated(EnumType.STRING)
    var role: Role,

    @OneToOne(
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true
    )
    val memberIdentification: MemberIdentification,
) : BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    val id: Long? = null

    fun updateRole(role: Role) {
        this.role = role
    }
}


