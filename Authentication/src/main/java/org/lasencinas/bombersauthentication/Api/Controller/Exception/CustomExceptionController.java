package org.lasencinas.bombersauthentication.Api.Controller.Exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.UUID;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class CustomExceptionController {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ApiError authenticationException(Throwable exception) {

        return new ApiError(createErrorId(), ApiErrorCatalog.UNAUTHORIZED.getCode(), HttpStatus.UNAUTHORIZED,
                exception.getMessage());
    }


    @ExceptionHandler({HttpClientErrorException.BadRequest.class, HttpMessageNotReadableException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError badRequestException(Throwable exception) {

        return new ApiError(createErrorId(), ApiErrorCatalog.REQUEST_NOT_VALID.getCode(), HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }

    /**
     * Create a new Error ID
     *
     * @return the new error id
     */
    private String createErrorId() {

        return UUID.randomUUID().toString();
    }

}