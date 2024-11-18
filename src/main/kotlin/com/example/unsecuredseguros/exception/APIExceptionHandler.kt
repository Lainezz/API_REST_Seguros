package com.example.unsecuredseguros.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.NumberFormatException

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(
        IllegalArgumentException::class
        , NumberFormatException::class
        , ValidationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequest(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        return ErrorRespuesta(e.message!!, request.requestURI)
    }


    @ExceptionHandler(NotFoundException::class) // Las "clases" (excepciones) que se quieren controlar
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNotFound(request: HttpServletRequest, e: Exception) : ErrorRespuesta {
        return ErrorRespuesta(e.message!!, request.requestURI)
    }

}