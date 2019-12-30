package org.lasencinas.bombersauthentication.Service.AuthUser;


import org.lasencinas.bombersauthentication.Api.Controller.Exception.ServiceException;
import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Converter.AuthUserConverter;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;
import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;
import org.lasencinas.bombersauthentication.Repository.AuthUserRepository;
import org.lasencinas.bombersauthentication.Repository.DniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static java.util.Collections.emptyList;


@Service("AuthUserService")
@ConditionalOnProperty(name = "test.active", havingValue = "false")
public class AuthUserImplementation implements AuthUserService {


    private final RestTemplate restTemplate;
    private AuthUserRepository authUserRepository;
    private DniRepository dniRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AuthUserConverter authUserConverter;

    @Value("${environment}")
    private String environment;

    @Autowired
    public AuthUserImplementation(AuthUserRepository authUserRepository, DniRepository dniRepository,
                                  BCryptPasswordEncoder bCryptPasswordEncoder, AuthUserConverter authUserConverter,
                                  RestTemplateBuilder restTemplateBuilder) {

        this.authUserRepository = authUserRepository;
        this.dniRepository = dniRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authUserConverter = authUserConverter;
        this.restTemplate = restTemplateBuilder.build();
    }


    public AuthUserDto createAuthUser(AuthUserDto authUserDto) {

        AuthUser user = authUserConverter.toDomainModel(authUserDto);
        HttpEntity<String> filterHttpEntity = new HttpEntity<>(new String());

        try {
            this.restTemplate.exchange(environment + "/dni?dni=" + authUserDto.getDni().getDni(),
                    HttpMethod.GET, filterHttpEntity,
                    Boolean.class);

        } catch (HttpServerErrorException | HttpClientErrorException ex) {
            throw new ServiceException.Builder(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                    .withMessage("Error while validating DNI... ")
                    .withCause(ex)
                    .build();

        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getDni().setAuthUser(user);

        Dni dni = dniRepository.save(user.getDni());
        user.setId(dni.getAuthUser().getId());

        return authUserConverter.toApiModel(user);
    }


    // In fact search by email but the name is given by the userDetails interface.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<AuthUser> user = this.authUserRepository.findByEmail(email);

        if (user.isPresent()) {

            return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                    user.get().getPassword(),
                    emptyList());
        }

        throw new UsernameNotFoundException(user.get().getEmail());
    }
}
