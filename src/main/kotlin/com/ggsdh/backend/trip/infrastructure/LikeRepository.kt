package com.ggsdh.backend.trip.infrastructure

import com.ggsdh.backend.trip.domain.MemberLike
import org.springframework.data.jpa.repository.JpaRepository

interface LikeRepository : JpaRepository<MemberLike, Long> {
    fun findAllByMemberId(memberId: Long): List<MemberLike>

    fun findByMemberIdAndLaneId(
        memberId: Long,
        laneId: Long,
    ): MemberLike?

    fun findByMemberIdAndTourAreaId(
        memberId: Long,
        tourAreaId: Long,
    ): MemberLike?

    fun deleteByMemberIdAndTourAreaId(
        memberId: Long,
        tourAreaId: Long,
    )

    fun deleteByMemberIdAndLaneId(
        memberId: Long,
        laneId: Long,
    )
}
