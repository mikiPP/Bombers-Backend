package org.lasencinas.bombersauthentication.Api.Controller.Auth;


import io.swagger.annotations.*;
import org.lasencinas.bombersauthentication.Api.ApiConstants;
import org.lasencinas.bombersauthentication.Api.Controller.Exception.ApiError;
import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;
import org.lasencinas.bombersauthentication.Service.AuthUser.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(AuthenticationController.API_AUTH_PATH)
@Api(value = "Authentication endpoint", tags = "authentication")
public class AuthenticationController {

    static final String API_AUTH_PATH = "/auth";
    static final String SIGN_UP_PATH = "/sign-up";
    static final String LOGGED_OUT_PATH = "/logged-out";

    private final AuthUserService authUserService;

    @Autowired
    public AuthenticationController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }


    @PostMapping(value = SIGN_UP_PATH)
    @ApiOperation(value = "Create an user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created", response = AuthUser.class),
            @ApiResponse(code = 400, message = ApiConstants.BAD_REQUEST_MESSAGE, response = ApiError.class),
            @ApiResponse(code = 500, message = ApiConstants.INTERNAL_SERVER_ERROR_MESSAGE, response = ApiError.class)})
    public AuthUserDto signUp(@ApiParam(value = "AuthUserTests Creation data") @RequestBody @Valid AuthUserDto authUserDto) {
        return this.authUserService.createAuthUser(authUserDto);
    }


    @GetMapping(value = LOGGED_OUT_PATH)
    @ApiOperation(value = "Logout an user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authentication OK", response = String.class),
            @ApiResponse(code = 400, message = ApiConstants.BAD_REQUEST_MESSAGE, response = ApiError.class),
            @ApiResponse(code = 401, message = ApiConstants.UNAUTHORIZED, response = ApiError.class),
            @ApiResponse(code = 500, message = ApiConstants.INTERNAL_SERVER_ERROR_MESSAGE, response = ApiError.class)})
    public String loggedOut() {
        return "Has salido de la sessión correctamente";
    }


    /**
     * Los métodos Login y Logout están creados por defecto por SprinSecurty en el path api/login , api/logout
     */
}
