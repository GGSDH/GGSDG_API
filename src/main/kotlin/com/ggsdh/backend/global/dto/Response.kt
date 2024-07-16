package com.ggsdh.backend.global.dto

class Response<T>(
    val result: Boolean,
    val message: String?,
    val data: T?,
) {
    companion object {
        fun <T> success(data: T): Response<T> {
            return Response(true, null, data)
        }

        fun <T> fail(message: String): Response<T> {
            return Response(false, message, null)
        }
    }
}
