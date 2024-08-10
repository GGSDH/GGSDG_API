package com.ggsdh.backend.trip.domain

import jakarta.persistence.*

@Entity
@Table(name = "TB_LANE_MAPPING")
class LaneMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_mapping_id")
    var id: Long? = null


    @JoinColumn(name = "tour_area_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var tourArea: TourArea? = null

    @JoinColumn(name = "lane_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var lane: Lane? = null;

    @Column(nullable = false)
    var sequence: Long? = null
}
