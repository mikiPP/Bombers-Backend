package org.lasencinas.dni.Service;

import com.google.common.base.Preconditions;
import org.lasencinas.dni.Model.Api.DniDto;
import org.lasencinas.dni.Model.Converter.DniConverter;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.lasencinas.dni.Repository.DniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("DniService")
@ConditionalOnProperty(name = "test.active", havingValue = "false")
public class DniServiceImplementation implements DniService {

    private final DniRepository dniRepository;
    private final DniConverter dniConverter;


    @Autowired
    DniServiceImplementation(DniRepository dniRepository, DniConverter dniConverter) {
        this.dniRepository = dniRepository;
        this.dniConverter = dniConverter;
    }


    /**
     * @param dni String
     * @return Boolean
     *
     * This method will call checkDni if this one have not raised any exception then will update the Dni with the given
     * Id.
     * */
    @Override
    public Boolean validateDni(String dni) {

        checkDni(dni);
        return true;
    }


    /**
     * @param  dni String
     * @Return Void
     *
     *  This method will check if the dni is valid if this dni is invalid will raise and exception .
     * */
    @Override
    public DniDto updateDni(String dni, Long id) {

        checkDni(dni);
        Dni dniUpdated = new Dni(dni);
        dniUpdated.setBomberId(id);

        return  dniConverter.toApiModel(dniRepository.save(dniUpdated));
    }



    /**
     * @Param String Dni
     * @Return Void
     *
     *  This method will check if the dni is valid if this dni is invalid will raise and exception .
     * */

    private void checkDni (String dni) {

        Optional<Dni>  optionalDni  = dniRepository.findById(dni);

        Preconditions.checkArgument(optionalDni.isPresent(),
                "The Dni does not exist in the data base");

        Preconditions.checkArgument(optionalDni.get().getBomberId() == 0,
                "The Dni has been used");

    }


}
