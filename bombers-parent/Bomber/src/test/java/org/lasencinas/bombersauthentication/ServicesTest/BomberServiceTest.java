package org.lasencinas.bombersauthentication.ServicesTest;

import com.mpp.commons.test.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.bombersauthentication.Service.BomberService;
import org.lasencinas.dni.Model.Api.DniDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class BomberServiceTest extends IntegrationTest {

    private Long Id = 1l;

    private int index = 0;

    private List<String> dnis = Arrays.asList("86854224Z", "25108985T", "45822494P", "41038536G");

    @Autowired
    private BomberService bomberService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void createAuthUserValidShouldReturnThatAuthUser() {

        //-------------------Given-------------------//

        DniDto dni = new DniDto();
        dni.setDni("52304534G");


        BomberDto bomberDto = createAuthUserDto();
        bomberDto.setDni(dni);

        //-------------------When-------------------//

        BomberDto newAuthUser = bomberService.createAuthUser(bomberDto);

        //-------------------Then-------------------//

        assertEquals(bomberDto.getEmail(), newAuthUser.getEmail());
        assertEquals(bomberDto.getDni().getDni(), newAuthUser.getDni().getDni());
        assertTrue(newAuthUser.getDni().getUserId() != null);
        assertTrue(bomberDto.getPassword() != newAuthUser.getPassword());
        assertTrue(bCryptPasswordEncoder.matches(bomberDto.getPassword(), newAuthUser.getPassword()));

    }


    @Test(expected = IllegalArgumentException.class)
    public void createAuthUserThatDniIsNotInTheDBShouldReturnThatAuthUser() {

        //-------------------Given-------------------//

        BomberDto bomberDto = createAuthUserDto();
        bomberDto.getDni().setDni("12346678-Z");

        //-------------------When-------------------//

        bomberService.createAuthUser(bomberDto);

        //-------------------Then-------------------//


    }

    @Test(expected = IllegalArgumentException.class)
    public void createAuthUserThatDniIsUsedShouldReturnThatAuthUser() {

        //-------------------Given-------------------//

        BomberDto bomberDto = createAuthUserDto();
        bomberService.createAuthUser(bomberDto);


        //-------------------When-------------------//

        bomberService.createAuthUser(bomberDto);

        //-------------------Then-------------------//


    }

    private BomberDto createAuthUserDto() {

        BomberDto bomberDto = new BomberDto();
        DniDto dni = new DniDto();
        dni.setDni(dnis.get(index));

        bomberDto.setId(Id);
        bomberDto.setEmail(String.format("test%s@test.com", Id));
        bomberDto.setDni(dni);
        bomberDto.setPassword("Test" + Id);

        Id++;
        index++;

        return bomberDto;
    }


    @Override
    protected void initializeIntegrationTest() {

    }
}
