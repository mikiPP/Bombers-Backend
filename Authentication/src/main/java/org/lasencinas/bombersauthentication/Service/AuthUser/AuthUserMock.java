package org.lasencinas.bombersauthentication.Service.AuthUser;


import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Converter.AuthUserConverter;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;
import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;
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
public class AuthUserMock implements AuthUserService {

    @Getter
    private List<Dni> dnis = new ArrayList<>(Arrays.asList(new Dni("86854224Z"), new Dni("25108985T"), new Dni(
                    "45822494P")
            , new Dni("41038536G"), new Dni("52304534G")));

    @Getter
    private List<AuthUser> authUsers = new ArrayList<>();

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private AuthUserConverter authUserConverter;


    @Autowired
    public AuthUserMock(BCryptPasswordEncoder bCryptPasswordEncoder, AuthUserConverter authUserConverter) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authUserConverter = authUserConverter;

    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public AuthUserDto createAuthUser(AuthUserDto authUserDto) {

        AuthUser authUser = authUserConverter.toDomainModel(authUserDto);

        int dniPosition = getDniPosition(authUser.getDni().getDni());

        Preconditions.checkArgument(dniPosition != -1,
                "The Dni does not exist in the data base");
        Preconditions.checkArgument(getDnis().get(dniPosition).getAuthUser() == null,
                "The Dni has been used");

        authUser.setPassword(bCryptPasswordEncoder.encode(authUser.getPassword()));
        authUser.getDni().setAuthUser(authUser);

        saveDni(authUser.getDni());

        return authUserConverter.toApiModel(saveAuthUser(authUser));
    }


    private AuthUser saveAuthUser(AuthUser authUser) {

        getAuthUsers().add(authUser);

        return authUser;
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
