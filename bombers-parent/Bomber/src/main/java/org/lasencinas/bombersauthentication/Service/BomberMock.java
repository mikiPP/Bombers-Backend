package org.lasencinas.bombersauthentication.Service.AuthUser;


import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.bombersauthentication.Model.Converter.BomberConverter;
import org.lasencinas.bombersauthentication.Service.BomberService;
import org.lasencinas.dni.Model.Domain.Dni.Dni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Service("AuthUserService")
@ConditionalOnProperty(name = "test.active", havingValue = "true")
public class BomberMock implements BomberService {

    @Getter
    private List<Dni> dnis = new ArrayList<>(Arrays.asList(new Dni("86854224Z"), new Dni("25108985T"), new Dni(
                    "45822494P")
            , new Dni("41038536G"), new Dni("52304534G")));

    @Getter
    private List<org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber> bombers = new ArrayList<>();

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private BomberConverter bomberConverter;


    @Autowired
    public BomberMock(BCryptPasswordEncoder bCryptPasswordEncoder, BomberConverter bomberConverter) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.bomberConverter = bomberConverter;

    }

    //this method will be not tested because is used by spring.
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public BomberDto createAuthUser(BomberDto bomberDto) {

        org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber bomber = bomberConverter.toDomainModel(bomberDto);

        int dniPosition = getDniPosition(bomber.getDni().getDni());

        Preconditions.checkArgument(dniPosition != -1,
                "The Dni does not exist in the data base");
        Preconditions.checkArgument(getDnis().get(dniPosition).getBomber() == null,
                "The Dni has been used");

        bomber.setPassword(bCryptPasswordEncoder.encode(bomber.getPassword()));
        bomber.getDni().setBomber(bomber);

        saveDni(bomber.getDni());

        return bomberConverter.toApiModel(saveAuthUser(bomber));
    }


    private org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber saveAuthUser(org.lasencinas.bombersauthentication.Model.Domain.AuthUser.Bomber bomber) {

        getBombers().add(bomber);

        return bomber;
    }

    private Dni saveDni(Dni dni) {

        int dniPosition = getDniPosition(dni.getDni());

        if (dniPosition == -1) {
            getDnis().add(dni);
        } else {
            getDnis().add(dniPosition, dni);
        }

        return dni;
    }


    private int getDniPosition(String dni) {

        Optional<Dni> optionalDni = getDnis()
                .stream()
                .filter(dniFromList -> dniFromList.getDni().equals(dni))
                .findFirst();

        return optionalDni.isPresent() ? getDnis().indexOf(optionalDni.get()) : -1;
    }
}
