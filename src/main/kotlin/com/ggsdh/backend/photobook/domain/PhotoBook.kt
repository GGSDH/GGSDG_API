package com.ggsdh.backend.photobook.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class PhotoBook(
    val id: Long,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    var photos: List<Photo>,
) {
    fun getPhotoTicket(): Photo? {
        return this.photos.find { it.isPhototicket }
    }

    fun getLocation(): Location? {
        return this.photos.firstOrNull()?.location
    }

    fun getDailyPhotoGroups(): List<DailyPhotoGroup> {
        val map: MutableMap<
                String,
                MutableMap<String, MutableList<Photo>>,
                > = mutableMapOf()

        this.photos
            .sortedBy {
                it.dateTime
            }.forEach {
                val dayKey = it.dateTime.toLocalDate().toString()
                val hourKey = it.dateTime.hour.toString()

                if (map[dayKey] == null) {
                    map[dayKey] = mutableMapOf()
                }

                if (map[dayKey]!![hourKey] == null) {
                    map[dayKey]!![hourKey] = mutableListOf()
                }

                map[dayKey]!![hourKey]!!.add(it)
            }

        val dailyPhotoGroups =
            map.toList().mapIndexed { index, it ->
                val date = LocalDate.parse(it.first)
                val hourlyPhotos =
                    it.second.toList().map {
                        val hour = it.first.toInt()
                        val photos = it.second
                        HourlyPhotoGroup(
                            LocalDateTime.of(date, LocalTime.of(hour, 0)),
                            photos,
                        )
                    }

                DailyPhotoGroup(
                    index + 1,
                    date,
                    hourlyPhotos,
                )
            }

        return dailyPhotoGroups
    }

    companion object {
        fun create(
            id: Long = -1,
            title: String,
            startDateTime: LocalDateTime,
            endDateTime: LocalDateTime,
            photos: List<Photo>,
        ): PhotoBook = PhotoBook(id, title, startDateTime, endDateTime, photos)
    }
}
