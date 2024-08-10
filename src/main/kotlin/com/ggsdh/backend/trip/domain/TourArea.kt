package com.ggsdh.backend.trip.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "TB_TOUR_AREA")
@DiscriminatorColumn(name = "content_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
class TourArea(
        @Enumerated(EnumType.STRING)
        var tripThemeConstants: TripThemeConstants,

        @Enumerated(EnumType.STRING)
        var sigunguCode: SigunguCode,

        var contentId: Long,
        var address1: String,
        var address2: String?,
        var image: String?,
        var latitude: Double,
        var longitude: Double,
        var mapLevel: Long?,
        var telNo: String?,
        var name: String,
        var ranking: Long?,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var dataModifiedAt: LocalDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var dataCreatedAt: LocalDate
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_area_id")
    var id: Long? = null

    @JoinColumn(name = "lane_mapping_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var laneMpping: LaneMapping? = null
}
