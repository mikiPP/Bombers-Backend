package org.lasencinas.dni.Service;

import org.springframework.stereotype.Service;

@Service
public interface DniService {

    Boolean validateDni(String dni);
}
