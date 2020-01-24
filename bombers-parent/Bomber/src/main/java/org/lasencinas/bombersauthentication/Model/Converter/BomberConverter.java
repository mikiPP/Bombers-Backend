package org.lasencinas.bombersauthentication.Model.Converter;

import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.dni.Model.Converter.DniConverter;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BomberConverter {

    private final DniConverter dniConverter;

    @Autowired
    BomberConverter(DniConverter dniConverter) {
        this.dniConverter = dniConverter;
    }


    public BomberDto toApiModel(org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber user) {

        BomberDto toApiModel = new BomberDto();

        toApiModel.setId(user.getId());
        toApiModel.setDni(dniConverter.toApiModel(user.getDni()));
        toApiModel.setEmail(user.getEmail());
        toApiModel.setPassword(user.getPassword());

        return toApiModel;
    }


    public org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber toDomainModel(BomberDto userDto) {

        org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber toDomainModel = new org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber();

        toDomainModel.setId(userDto.getId());
        toDomainModel.setPassword(userDto.getPassword());
        toDomainModel.setEmail(userDto.getEmail());

        Dni dni = new Dni();
        dni.setBomber(toDomainModel);
        dni.setDni(userDto.getDni().getDni());

        toDomainModel.setDni(dni);

        return toDomainModel;
    }

}
