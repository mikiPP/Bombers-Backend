package org.lasencinas.bombersauthentication.Service;


import com.mpp.commons.Exception.InvalidDniException;
import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.bombersauthentication.Model.Converter.BomberConverter;
import org.lasencinas.bombersauthentication.Model.Domain.Bomber;
import org.lasencinas.bombersauthentication.Repository.BomberRepository;
import org.lasencinas.dni.Model.Api.DniDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private BomberConverter bomberConverter;

    @Value("${environment}")
    private String environment;

    @Autowired
    public BomberImplementation(BomberRepository bomberRepository,
                                BCryptPasswordEncoder bCryptPasswordEncoder, BomberConverter bomberConverter,
                                RestTemplateBuilder restTemplateBuilder) {

        this.bomberRepository = bomberRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.bomberConverter = bomberConverter;
        this.restTemplate = restTemplateBuilder.build();
    }


    public BomberDto insertBomber(BomberDto bomberDto) throws InvalidDniException {

        validateUserDni(bomberDto.getDni());

        Bomber bomber = bomberConverter.toDomainModel(bomberDto);
        bomber.setPassword(bCryptPasswordEncoder.encode(bomber.getPassword()));

        bomberDto = bomberConverter.toApiModel(bomber);
        updateDni(bomberDto.getDni(), bomberDto.getId());


        return bomberDto;
    }

    // In fact search by email but the name is given by the userDetails interface.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<org.lasencinas.bombersauthentication.Model.Domain.Bomber> user = this.bomberRepository.findByEmail(email);

        if (user.isPresent()) {

            return new User(user.get().getEmail(),
                    user.get().getPassword(),
                    emptyList());
        }

        throw new UsernameNotFoundException(user.get().getEmail());
    }

    private void validateUserDni(String dni) throws InvalidDniException {

        HttpEntity<String> filterHttpEntity = new HttpEntity<>("");

        try {
            this.restTemplate.exchange(environment + "/dni?dni=" + dni,
                    HttpMethod.GET, filterHttpEntity,
                    Boolean.class);

        } catch (HttpServerErrorException | HttpClientErrorException ex) {
            throw new InvalidDniException("Error while validate dni: " + dni);
        }
    }

    private void updateDni(String dni, Long id) throws InvalidDniException {

        HttpEntity<String> filterHttpEntity = new HttpEntity<>("");
        try {
            this.restTemplate.exchange(environment + "/dni?dni" + dni + "&id=" + id,
                    HttpMethod.PUT, filterHttpEntity,
                    DniDto.class);

        } catch (HttpServerErrorException | HttpClientErrorException ex) {
            throw new InvalidDniException("Error while validate dni: " + dni);
        }
    }

}
