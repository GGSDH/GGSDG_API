package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.*

@Entity
@Table(name = "TB_TRIP_THEME")
class TripTheme(
        @JoinColumn(name = "member_id")
        @ManyToOne(fetch = FetchType.LAZY)
        val member: Member,
        
        @Column(name = "theme")
        @Enumerated(EnumType.STRING)
        val tripThemeConstants: TripThemeConstants,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_theme_id")
    var id: Long? = null
}
