package com.ggsdh.backend.trip.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "TB_MEMBER_LIKE")
class MemberLike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_like_id")
    var id: Long? = null,
    var memberId: Long,
    var tourAreaId: Long?,
    var laneId: Long?,
)
