package org.lasencinas.bombersauthentication.Model.Converter;

import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.bombersauthentication.Model.Domain.Bomber;
import org.lasencinas.dni.Model.Converter.DniConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BomberConverter {

    private final DniConverter dniConverter;

    @Autowired
    BomberConverter(DniConverter dniConverter) {
        this.dniConverter = dniConverter;
    }


    public BomberDto toApiModel(Bomber bomber) {

        BomberDto toApiModel = new BomberDto();

        toApiModel.setId(bomber.getId());
        toApiModel.setDni(bomber.getDni());
        toApiModel.setEmail(bomber.getEmail());
        toApiModel.setPassword(bomber.getPassword());

        return toApiModel;
    }


    public org.lasencinas.bombersauthentication.Model.Domain.

            Bomber toDomainModel(BomberDto bomberDto) {

        Bomber toDomainModel = new Bomber();

        toDomainModel.setId(bomberDto.getId());
        toDomainModel.setPassword(bomberDto.getPassword());
        toDomainModel.setEmail(bomberDto.getEmail());
        toDomainModel.setDni(bomberDto.getDni());

        return toDomainModel;
    }

}
