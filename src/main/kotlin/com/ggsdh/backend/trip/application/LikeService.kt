package com.ggsdh.backend.trip.application

import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.trip.domain.MemberLike
import com.ggsdh.backend.trip.exception.TripError
import com.ggsdh.backend.trip.infrastructure.LaneRepository
import com.ggsdh.backend.trip.infrastructure.LikeRepository
import com.ggsdh.backend.trip.infrastructure.TourAreaRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LikeService(
    private val likeRepository: LikeRepository,
    private val laneRepository: LaneRepository,
    private val tourAreaRepository: TourAreaRepository,
) {
    fun getAllLikedTourAreaIdsByMember(memberId: Long): List<Long> {
        val likes = likeRepository.findAllByMemberId(memberId)
        return likes.filter { it.tourAreaId != null }.map { it.tourAreaId!! }
    }

    fun getAllLikedLaneIdsByMember(memberId: Long): List<Long> {
        val likes = likeRepository.findAllByMemberId(memberId)
        return likes.filter { it.laneId != null }.map { it.laneId!! }
    }

    @Transactional
    fun unlikeTourArea(
        memberId: Long,
        tourAreaId: Long,
    ) {
        val isLiked = this.isTourAreaLikedByMember(memberId, tourAreaId)
        if (!isLiked) {
            throw BusinessException(TripError.NOT_LIKED)
        }

        likeRepository.deleteByMemberIdAndTourAreaId(memberId, tourAreaId)

        val tourArea = tourAreaRepository.findById(tourAreaId).get()
        tourArea.likes--

        tourAreaRepository.save(tourArea)
    }

    @Transactional
    fun unlikeLane(
        memberId: Long,
        laneId: Long,
    ) {
        val isLiked = this.isLaneLikedByMember(memberId, laneId)
        if (!isLiked) {
            throw BusinessException(TripError.NOT_LIKED)
        }

        likeRepository.deleteByMemberIdAndLaneId(memberId, laneId)

        val lane = laneRepository.findById(laneId).get()
        lane.likes--

        laneRepository.save(lane)
    }

    @Transactional
    fun likeTourArea(
        memberId: Long,
        tourAreaId: Long,
    ) {
        val isLiked = this.isTourAreaLikedByMember(memberId, tourAreaId)
        if (isLiked) {
            throw BusinessException(TripError.ALREADY_LIKED)
        }

        likeRepository.save(
            MemberLike(
                memberId = memberId,
                tourAreaId = tourAreaId,
                laneId = null,
            ),
        )

        val tourArea = tourAreaRepository.findById(tourAreaId).get()
        tourArea.likes++

        tourAreaRepository.save(tourArea)
    }

    @Transactional
    fun likeLane(
        memberId: Long,
        laneId: Long,
    ) {
        val isLiked = this.isLaneLikedByMember(memberId, laneId)
        if (isLiked) {
            throw BusinessException(TripError.ALREADY_LIKED)
        }

        likeRepository.save(
            MemberLike(
                memberId = memberId,
                tourAreaId = null,
                laneId = laneId,
            ),
        )

        val lane = laneRepository.findById(laneId).get()
        lane.likes++

        laneRepository.save(lane)
    }

    @Transactional
    fun likeAILane(
        memberId: Long,
        laneId: Long,
    ) {
        val lane = laneRepository.findById(laneId).get()
        lane.aiLaneSavedBy = memberId

        likeLane(memberId, laneId)

        laneRepository.save(lane)
    }

    @Transactional
    fun unLikeAILane(
        memberId: Long,
        laneId: Long,
    ) {
        val lane = laneRepository.findById(laneId).get()
        lane.aiLaneSavedBy = null

        unlikeLane(memberId, laneId)

        laneRepository.save(lane)
    }

    fun isTourAreaLikedByMember(
        memberId: Long,
        tourAreaId: Long,
    ): Boolean = likeRepository.findByMemberIdAndTourAreaId(memberId, tourAreaId) != null

    fun isLaneLikedByMember(
        memberId: Long,
        laneId: Long,
    ): Boolean = likeRepository.findByMemberIdAndLaneId(memberId, laneId) != null
}
