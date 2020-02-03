package org.lasencinas.dni.Service;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.lasencinas.dni.Model.Api.DniDto;
import org.lasencinas.dni.Model.Converter.DniConverter;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("DniService")
@ConditionalOnProperty(name = "test.active", havingValue = "true")
public class DniServiceMock implements DniService {


    private final DniConverter dniConverter;

    @Getter
    private List<Dni> dniRepository = new ArrayList<>(Arrays.asList(new Dni("86854224Z"), new Dni("25108985T"), new Dni(
                    "45822494P")
            , new Dni("41038536G",1L), new Dni("52304534G")));

    @Autowired
    public DniServiceMock(DniConverter dniConverter) {
        this.dniConverter = dniConverter;
    }


    /**
     * @Param String Dni
     * @Return Boolean
     *
     * This method will call checkDni if this one have not raised any exception then will come back and return true.
     * */

    @Override
    public Boolean validateDni(String dni) {

        checkDni(dni);
        return true;
    }

    /**
     * @param dni String
     * @return Boolean
     *
     * This method will call checkDni if this one have not raised any exception then will update the Dni with the given
     * Id.
     * */

    @Override
    public DniDto updateDni(String dni, Long id) {

        checkDni(dni);

        Dni dniUpdated = new Dni(dni);

        dniUpdated.setBomberId(id);

        getDniRepository().add(dniUpdated);

        return dniConverter.toApiModel(dniUpdated);
    }



    /**
     * @param  dni String
     * @Return Void
     *
     *  This method will check if the dni is valid if this dni is invalid will raise and exception .
     * */

    private void checkDni (String dni) {

        Optional<Dni>  optionalDni  = findById(dni);

        Preconditions.checkArgument(optionalDni.isPresent(),
                "The Dni does not exist in the data base");

        Preconditions.checkArgument(optionalDni.get().getBomberId() == 0,
                "The Dni has been used");

    }

    private Optional<Dni> findById(String dni) {

        return getDniRepository()
                .stream()
                .filter(dniFromArray -> dniFromArray.getDni().equals(dni))
                .findFirst();
    }
}
