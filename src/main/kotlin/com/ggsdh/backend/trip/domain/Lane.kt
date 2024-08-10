package com.ggsdh.backend.trip.domain

import jakarta.persistence.*
import jakarta.persistence.CascadeType.REMOVE

@Entity
@Table(name = "TB_LANE")
class Lane(
        var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_id")
    var id: Long? = null

    @OneToMany(
            targetEntity = LaneMapping::class,
            orphanRemoval = true,
            cascade = [REMOVE]
    )
    var laneMappings: List<LaneMapping>? = listOf()
}
