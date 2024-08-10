package com.ggsdh.backend.trip.domain

import jakarta.persistence.*

@Entity
@Table(name = "TB_LANE")
class Lane() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lane_id")
    var id: Long? = null
}
