package org.lasencinas.bombersauthentication.Model.Converter;

import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;
import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUserConverter {

    private final DniConverter dniConverter;

    @Autowired
    AuthUserConverter(DniConverter dniConverter) {
        this.dniConverter = dniConverter;
    }


    public AuthUserDto toApiModel(AuthUser user) {

        AuthUserDto toApiModel = new AuthUserDto();

        toApiModel.setId(user.getId());
        toApiModel.setDni(dniConverter.toApiModel(user.getDni()));
        toApiModel.setEmail(user.getEmail());
        toApiModel.setPassword(user.getPassword());

        return toApiModel;
    }


    public AuthUser toDomainModel(AuthUserDto userDto) {

        AuthUser toDomainModel = new AuthUser();

        toDomainModel.setId(userDto.getId());
        toDomainModel.setPassword(userDto.getPassword());
        toDomainModel.setEmail(userDto.getEmail());

        Dni dni = new Dni();
        dni.setUser(toDomainModel);
        dni.setDni(userDto.getDni().getDni());

        toDomainModel.setDni(dni);

        return toDomainModel;
    }

}
