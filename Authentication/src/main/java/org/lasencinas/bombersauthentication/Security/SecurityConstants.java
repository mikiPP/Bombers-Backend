package org.lasencinas.bombersauthentication.Security;

public final class SecurityConstants {

    static final String SECRET = "SecretKeyToGenJWTs";
    static final long EXPIRATION_TIME = 21_600_000; //6 hours
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";
    static final String SIGN_UP_URL = "/auth/sign-up";
    static final String LOGGED_OUT_URL = "/auth/logged-out";
    static final String LOGGIN_URL = "/login";
    static final String VALIDATE_DNI_URL = "dni/**";


}
