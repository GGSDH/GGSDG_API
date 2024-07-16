package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.*

@Entity
@Table(name = "t_trip_theme")
class TripTheme(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    val member: Member,

    @Column(name = "theme")
    val tripThemeConstants: TripThemeConstants
) : BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "trip_theme_id")
    var id: Long? = null
        private set
}
