package com.ggsdh.backend.global.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.ggsdh.backend.global.exception.error.BusinessException
import com.ggsdh.backend.global.exception.error.ErrorResponse
import com.ggsdh.backend.global.exception.error.GlobalError
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class ApiExceptionHandlingFilter(
    private val om: ObjectMapper,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
    ) {
        try {
            chain.doFilter(request, response)
        } catch (exception: BusinessException) {
            setErrorResponse(response, exception)
        } catch (exception: Exception) {
            setErrorResponse(response, BusinessException(GlobalError.INTERNAL_SERVER_ERROR))
        }
    }

    private fun setErrorResponse(
        response: HttpServletResponse,
        exception: BusinessException,
    ) = try {
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = 500
        om.writeValue(response.outputStream, ErrorResponse(exception.errorCode))
    } catch (exception: IOException) {
        throw RuntimeException(exception)
    }
}
