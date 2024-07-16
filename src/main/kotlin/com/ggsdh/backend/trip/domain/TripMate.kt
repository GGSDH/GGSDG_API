package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.trip.domain.constants.TripMateConstants
import jakarta.persistence.*

@Entity
@Table(name = "TB_TRIP_MATE")
class TripMate(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    val member: Member,

    @Column(name = "TRIP_MATE")
    val tripMateConstants: TripMateConstants
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRIP_MATE_ID")
    var id: Long? = null
        private set
}
