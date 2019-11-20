package org.lasencinas.bombersauthentication.Model.Converter;

import org.lasencinas.bombersauthentication.Model.Api.DniDto;
import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;
import org.springframework.stereotype.Component;

@Component
public class DniConverter {


    public DniDto toApiModel(Dni dni) {

        DniDto toApiModel = new DniDto();

        toApiModel.setDni(dni.getDni());
        toApiModel.setUserId(dni.getAuthUser().getId());

        return toApiModel;
    }


}