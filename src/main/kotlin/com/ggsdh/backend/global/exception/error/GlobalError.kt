package com.ggsdh.backend.global.exception.error

import org.springframework.http.HttpStatus

enum class GlobalError(
    override val message: String,
    override val status: HttpStatus,
    override val code: String,
) : ErrorCode {
    PHOTOBOOK_NOT_FOUND("포토북이 존재하지 않습니다.", HttpStatus.NOT_FOUND, "P_001"),
    GLOBAL_NOT_FOUND("리소스가 존재하지 않습니다.", HttpStatus.NOT_FOUND, "G_001"),
    INVALID_REQUEST_PARAM("요청 파라미터가 유효하지 않습니다.", HttpStatus.BAD_REQUEST, "G_002"),
    INTERNAL_SERVER_ERROR("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR, "G_003"),
    PHOTOBOOK_ALREADY_HAS_PHOTOTICKET("이미 포토티켓이 존재합니다.", HttpStatus.BAD_REQUEST, "P_002"),
}
