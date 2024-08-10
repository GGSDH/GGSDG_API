package com.ggsdh.backend.trip.domain

import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.*
import jakarta.persistence.CascadeType.REMOVE

@Entity
@Table(name = "TB_LANE")
class Lane(
        var name: String,

        @Enumerated(value = EnumType.STRING)
        var tripThemeConstants: TripThemeConstants
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
