package org.lasencinas.dni.Service;

import org.lasencinas.dni.Model.Api.DniDto;
import org.springframework.stereotype.Service;

@Service
public interface DniService {

    Boolean validateDni(String dni);

    DniDto updateDni(String dni, Long id);
}
