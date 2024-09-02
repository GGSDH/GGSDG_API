package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "TB_LANE")
class Lane(
    var name: String,
    @Enumerated(value = EnumType.STRING)
    var tripThemeConstants: TripThemeConstants,
    var likes: Long = 0,
    var isAI: Boolean = false,
    var aiLaneSavedBy: Long? = null,
    val days: Int = 1,
    val description: String? = null,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_id")
    var id: Long? = null
}
