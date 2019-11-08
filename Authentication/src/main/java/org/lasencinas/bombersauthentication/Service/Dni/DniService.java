package org.lasencinas.bombersauthentication.Service.Dni;

import org.springframework.stereotype.Service;

@Service
public interface DniService {

    Boolean validateDni(String dni);
}
