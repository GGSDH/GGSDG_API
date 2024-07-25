package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.member.domain.Member
import com.ggsdh.backend.trip.domain.constants.TripThemeConstant
import jakarta.persistence.*

@Entity
@Table(name = "TB_TRIP_THEME")
class TripTheme(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    val member: Member,
    @Column(name = "theme")
    @Enumerated(EnumType.STRING)
    val tripThemeConstants: TripThemeConstant,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_theme_id")
    var id: Long? = null
        private set
}
