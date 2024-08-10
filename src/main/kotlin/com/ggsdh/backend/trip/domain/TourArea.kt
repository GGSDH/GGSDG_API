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
open class TourArea(
        @Enumerated(EnumType.STRING)
        open var tripThemeConstants: TripThemeConstants,

        @Enumerated(EnumType.STRING)
        open var sigunguCode: SigunguCode,

        open var contentId: Long,
        open var address1: String,
        open var address2: String?,
        open var image: String?,
        open var latitude: Double,
        open var longitude: Double,
        open var mapLevel: Long?,
        open var telNo: String?,
        open var name: String,
        open var ranking: Long?,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        open var dataModifiedAt: LocalDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        open var dataCreatedAt: LocalDate
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_area_id")
    open var id: Long? = null
}
