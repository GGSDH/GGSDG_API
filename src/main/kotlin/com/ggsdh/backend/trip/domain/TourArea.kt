package com.ggsdh.backend.trip.domain

import com.fasterxml.jackson.annotation.JsonFormat
import com.ggsdh.backend.global.auditing.BaseEntity
import com.ggsdh.backend.trip.domain.constants.SigunguCode
import com.ggsdh.backend.trip.domain.constants.TripThemeConstants
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "TB_TOUR_AREA")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "contentType")
abstract class TourArea(
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
        var rank: Long?,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var dataModifiedAt: LocalDate,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        var dataCreatedAt: LocalDate
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_area_id")
    var id: Long? = null
        private set

    @Entity
    @Table(name = "TB_TOUR_AREA-TOURISM_SPOT")
    @DiscriminatorValue("TOURISM_SPOT")
    class TourismSpotArea(
            tripThemeConstants: TripThemeConstants,
            sigunguCode: SigunguCode,
            contentId: Long,
            address1: String,
            address2: String?,
            image: String?,
            latitude: Double,
            longitude: Double,
            mapLevel: Long?,
            telNo: String?,
            name: String,
            dataModifiedAt: LocalDate,
            dataCreatedAt: LocalDate,
            rank: Long?,
            var grandTourYn: Boolean,
            var associatedGrandTourYn: Boolean
    ) : TourArea(
            tripThemeConstants,
            sigunguCode,
            contentId,
            address1,
            address2,
            image,
            latitude,
            longitude,
            mapLevel,
            telNo,
            name,
            rank,
            dataModifiedAt,
            dataCreatedAt
    )

    @Entity
    @Table(name = "TB_TOUR_AREA-FESTIVAL_EVENT")
    @DiscriminatorValue("FESTIVAL_EVENT")
    class FestivalEvent(
            tripThemeConstants: TripThemeConstants,
            sigunguCode: SigunguCode,
            contentId: Long,
            address1: String,
            address2: String?,
            image: String?,
            latitude: Double,
            longitude: Double,
            mapLevel: Long?,
            telNo: String?,
            name: String,
            rank: Long?,
            dataModifiedAt: LocalDate,
            dataCreatedAt: LocalDate,
            var sponsorName: String?,
            var startDate: LocalDate?,
            var endDate: LocalDate?,
            var playTime: String?,
            var ageLimit: String?,
            var eventPlace: String?,
            var spendTimeFestival: String?,
            var usetimeFestival: String?,
            var discountInfo: String?
    ) : TourArea(
            tripThemeConstants,
            sigunguCode,
            contentId,
            address1,
            address2,
            image,
            latitude,
            longitude,
            mapLevel,
            telNo,
            name,
            rank,
            dataModifiedAt,
            dataCreatedAt
    )

    @Entity
    @Table(name = "TB_TOUR_AREA-RESTAURANT")
    @DiscriminatorValue("RESTAURANT")
    class Restaurant(
            tripThemeConstants: TripThemeConstants,
            sigunguCode: SigunguCode,
            contentId: Long,
            address1: String,
            address2: String?,
            image: String?,
            latitude: Double,
            longitude: Double,
            mapLevel: Long?,
            telNo: String?,
            name: String,
            rank: Long?,
            dataModifiedAt: LocalDate,
            dataCreatedAt: LocalDate,
            var firstMenuImage: String?,
            var firstMenuName: String?
    ) : TourArea(
            tripThemeConstants,
            sigunguCode,
            contentId,
            address1,
            address2,
            image,
            latitude,
            longitude,
            mapLevel,
            telNo,
            name,
            rank,
            dataModifiedAt,
            dataCreatedAt
    ) {
    }
}
