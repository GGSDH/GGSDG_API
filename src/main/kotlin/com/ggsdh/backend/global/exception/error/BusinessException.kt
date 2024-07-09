package com.ggsdh.backend.global.exception.error

class BusinessException(
    val errorCode: ErrorCode,
) : RuntimeException(errorCode.message)
