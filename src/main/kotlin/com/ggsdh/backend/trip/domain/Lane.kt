package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.*

@Entity
@Table(name = "TB_LANE")
class Lane(
    var name: String,
    @Enumerated(value = EnumType.STRING)
    var tripThemeConstants: TripThemeConstants,
    var likes: Long = 0,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_id")
    var id: Long? = null
}
