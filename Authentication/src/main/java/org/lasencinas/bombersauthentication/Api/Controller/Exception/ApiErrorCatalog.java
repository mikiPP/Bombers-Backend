package org.lasencinas.bombersauthentication.Api.Controller.Exception;

public enum ApiErrorCatalog {
    GENERIC_ERROR("API-01"),
    METHOD_NOT_SUPPORTED("API-02"),
    MESSAGE_NOT_READABLE("API-03"),
    REQUEST_PARAMS_NOT_VALID("API-04"),
    MEDIA_TYPE_ERROR("API-05"),
    REQUEST_NOT_VALID("API-06"),
    RESOURCE_NOT_FOUND("API-07"),
    DATABASE_ERROR("API-08"),
    UNSUPPORTED_OPERATION("API-09"),
    UNAUTHORIZED("API-10"),
    FORBIDDEN("API-11");

    private final String code;

    private ApiErrorCatalog(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}