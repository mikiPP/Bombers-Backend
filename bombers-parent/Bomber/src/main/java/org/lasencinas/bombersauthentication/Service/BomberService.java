package org.lasencinas.bombersauthentication.Service;

import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface BomberService extends UserDetailsService {

    BomberDto createAuthUser(BomberDto user);
}
