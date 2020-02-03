package org.lasencinas.dni.Model.Converter;

import org.lasencinas.dni.Model.Api.DniDto;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.springframework.stereotype.Component;

@Component
public class DniConverter {


    public DniDto toApiModel(Dni dni) {

        DniDto toApiModel = new DniDto();

        toApiModel.setDni(dni.getDni());
        toApiModel.setBomberId(dni.getBomberId());

        return toApiModel;
    }


}