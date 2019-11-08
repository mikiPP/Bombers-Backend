package org.lasencinas.bombersauthentication.Service.Dni;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service("DniService")
@ConditionalOnProperty(name = "test.active" , havingValue = "true")
public class DniServiceMock implements DniService{

    @Override
    public Boolean validateDni(String dni) {
        return null;
    }
}
