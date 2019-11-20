package org.lasencinas.bombersauthentication.Service.Dni;

import com.google.common.base.Preconditions;
import org.lasencinas.bombersauthentication.Repository.DniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service("DniService")
@ConditionalOnProperty(name = "test.active", havingValue = "false")
public class DniServiceImplementation implements DniService {

    private final DniRepository dniRepository;


    @Autowired
    DniServiceImplementation(DniRepository dniRepository) {
        this.dniRepository = dniRepository;
    }


    /**
     * This method will return true (then the dni is valid) when dni is in the database
     * and don't have any user associated yet.
     *
     * @param dni String
     * @return Boolean
     */
    @Override
    public Boolean validateDni(String dni) {

        Preconditions.checkArgument(dniRepository.findById(dni).isPresent(),
                "The Dni does not exist in the data base");

        Preconditions.checkArgument(dniRepository.findById(dni).get().getAuthUser() == null,
                "The Dni has been used");

        return true;
    }
}
