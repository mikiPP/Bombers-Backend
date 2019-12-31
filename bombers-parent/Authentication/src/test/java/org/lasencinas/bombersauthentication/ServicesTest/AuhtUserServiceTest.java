package org.lasencinas.bombersauthentication.ServicesTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.bombersauthentication.IntegrationTest;
import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Api.DniDto;
import org.lasencinas.bombersauthentication.Service.AuthUser.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class AuhtUserServiceTest extends IntegrationTest {

    private Long Id = 1l;

    private int index = 0;

    private List<String> dnis = Arrays.asList("86854224Z", "25108985T", "45822494P", "41038536G");

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void createAuthUserValidShouldReturnThatAuthUser() {

        //-------------------Given-------------------//

        DniDto dni = new DniDto();
        dni.setDni("52304534G");


        AuthUserDto authUserDto = createAuthUserDto();
        authUserDto.setDni(dni);

        //-------------------When-------------------//

        AuthUserDto newAuthUser = authUserService.createAuthUser(authUserDto);

        //-------------------Then-------------------//

        assertEquals(authUserDto.getEmail(), newAuthUser.getEmail());
        assertEquals(authUserDto.getDni().getDni(), newAuthUser.getDni().getDni());
        assertTrue(newAuthUser.getDni().getUserId() != null);
        assertTrue(authUserDto.getPassword() != newAuthUser.getPassword());
        assertTrue(bCryptPasswordEncoder.matches(authUserDto.getPassword(), newAuthUser.getPassword()));

    }


    @Test(expected = IllegalArgumentException.class)
    public void createAuthUserThatDniIsNotInTheDBShouldReturnThatAuthUser() {

        //-------------------Given-------------------//

        AuthUserDto authUserDto = createAuthUserDto();
        authUserDto.getDni().setDni("12346678-Z");

        //-------------------When-------------------//

        authUserService.createAuthUser(authUserDto);

        //-------------------Then-------------------//


    }

    @Test(expected = IllegalArgumentException.class)
    public void createAuthUserThatDniIsUsedShouldReturnThatAuthUser() {

        //-------------------Given-------------------//

        AuthUserDto authUserDto = createAuthUserDto();
        authUserService.createAuthUser(authUserDto);


        //-------------------When-------------------//

        authUserService.createAuthUser(authUserDto);

        //-------------------Then-------------------//


    }

    private AuthUserDto createAuthUserDto() {

        AuthUserDto authUserDto = new AuthUserDto();
        DniDto dni = new DniDto();
        dni.setDni(dnis.get(index));

        authUserDto.setId(Id);
        authUserDto.setEmail(String.format("test%s@test.com", Id));
        authUserDto.setDni(dni);
        authUserDto.setPassword("Test" + Id);

        Id++;
        index++;

        return authUserDto;
    }


    @Override
    protected void initializeIntegrationTest() {

    }
}
