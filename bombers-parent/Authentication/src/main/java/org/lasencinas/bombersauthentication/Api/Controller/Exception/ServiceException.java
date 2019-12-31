package org.lasencinas.bombersauthentication.Api.Controller.Exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {

    private final String code;
    private final HttpStatus httpStatus;


    private ServiceException(String code, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public static Builder getBuilder() {

        return new Builder();
    }

    public String getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public static class Builder {
        private String code;
        private String message;
        private HttpStatus httpStatus;
        private Throwable cause;


        private Builder() {
        }

        public Builder(String code) {
            this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            this.code = code;
        }

        public ServiceException.Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public ServiceException.Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ServiceException.Builder withHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ServiceException.Builder withCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public ServiceException build() {
            return new ServiceException(this.code, this.httpStatus, this.message, this.cause);
        }
    }
}