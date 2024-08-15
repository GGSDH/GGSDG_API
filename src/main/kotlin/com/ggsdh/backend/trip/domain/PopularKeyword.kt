package com.ggsdh.backend.trip.domain

import jakarta.persistence.*

@Entity
@Table(name = "TB_POPULAR_KEYWORD")
class PopularKeyword(
        var keyword: String,

        var count: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "popular_keyword_id")
    var id: Long? = null
}
