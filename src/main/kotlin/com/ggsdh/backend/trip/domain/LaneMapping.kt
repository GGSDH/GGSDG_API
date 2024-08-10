package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.global.auditing.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "TB_LANE_MAPPING")
class LaneMapping : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_mapping_id")
    var id: Long? = null

    @Column(nullable = false)
    var sequence: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lane_id")
    var lane: Lane? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_area_id")
    var tourArea: TourArea? = null
}
