package org.lasencinas.bombersauthentication.Service.AuthUser;

import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface AuthUserService extends UserDetailsService {

    AuthUserDto createAuthUser (AuthUserDto user);
}
