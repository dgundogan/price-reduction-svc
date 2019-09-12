package co.uk.e2x.handler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(MethodArgumentTypeMismatchException::class)])
    fun handleInvalidEnumParams(
        ex: MethodArgumentTypeMismatchException,
        request: WebRequest
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            Date(),
            "Invalid Parameter",
            ex.message!!
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(HttpClientErrorException::class)])
    fun handleInvalidProductId(ex: HttpClientErrorException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            Date(),
            "Invalid Product Id",
            ex.message!!
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(ProductNotFoundException::class)])
    fun handleNotFoundProductId(ex: ProductNotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            Date(),
            "Product not found",
            ex.message!!
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

}