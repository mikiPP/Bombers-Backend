package org.lasencinas.dni.Service;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("DniService")
@ConditionalOnProperty(name = "test.active", havingValue = "true")
public class DniServiceMock implements DniService {

    @Getter
    private List<Dni> dnis = new ArrayList<>(Arrays.asList(new Dni("86854224Z"), new Dni("25108985T"), new Dni(
                    "45822494P")
            , new Dni("41038536G"), new Dni("52304534G")));


    @Override
    public Boolean validateDni(String dni) {

        Preconditions.checkArgument(findById(dni).isPresent(),
                "The Dni does not exist in the data base");

        Preconditions.checkArgument(findById(dni).get().getBomber() == null,
                "The Dni has been used");

        return true;
    }


    private Optional<Dni> findById(String dni) {

        return getDnis()
                .stream()
                .filter(dniFromArray -> dniFromArray.getDni().equals(dni))
                .findFirst();
    }
}
