package org.lasencinas.bombersauthentication.Service;


import com.mpp.commons.Api.Exception.ServiceException;
import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.bombersauthentication.Model.Converter.BomberConverter;
import org.lasencinas.bombersauthentication.Repository.BomberRepository;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.lasencinas.dni.Repository.DniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
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
public class BomberImplementation implements BomberService {

    private final RestTemplate restTemplate;
    private BomberRepository bomberRepository;
    private DniRepository dniRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private BomberConverter bomberConverter;

    @Value("${environment}")
    private String environment;

    @Autowired
    public BomberImplementation(BomberRepository bomberRepository, DniRepository dniRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder, BomberConverter bomberConverter,
                                RestTemplateBuilder restTemplateBuilder) {

        this.bomberRepository = bomberRepository;
        this.dniRepository = dniRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.bomberConverter = bomberConverter;
        this.restTemplate = restTemplateBuilder.build();
    }


    public BomberDto createAuthUser(BomberDto bomberDto) {

        org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber user = bomberConverter.toDomainModel(bomberDto);

        validateUserDni(bomberDto.getDni().getDni());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getDni().setBomber(user);

        Dni dni = dniRepository.save(user.getDni());
        user.setId(dni.getBomber().getId());

        return bomberConverter.toApiModel(user);
    }

    // In fact search by email but the name is given by the userDetails interface.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber> user = this.bomberRepository.findByEmail(email);

        if (user.isPresent()) {

            return new User(user.get().getEmail(),
                    user.get().getPassword(),
                    emptyList());
        }

        throw new UsernameNotFoundException(user.get().getEmail());
    }

    private void validateUserDni(String dni) {

        HttpEntity<String> filterHttpEntity = new HttpEntity<>("");

        try {
            this.restTemplate.exchange(environment + "/dni?dni=" + dni,
                    HttpMethod.GET, filterHttpEntity,
                    Boolean.class);

        } catch (HttpServerErrorException | HttpClientErrorException ex) {
            throw new ServiceException.Builder(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                    .withMessage("Error while validating DNI... ")
                    .withCause(ex)
                    .build();

        }
    }

}
