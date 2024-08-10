package com.ggsdh.backend.trip.exception

import com.ggsdh.backend.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

enum class TripError(
        override val message: String,
        override val status: HttpStatus,
        override val code: String,
) : ErrorCode {
    LANE_NOT_FOUND("해당 노선이 존재하지 않습니다.", HttpStatus.NOT_FOUND, "T_001"),
}
