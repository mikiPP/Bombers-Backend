package org.lasencinas.bombersauthentication.ServicesTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.bombersauthentication.IntegrationTest;
import org.lasencinas.bombersauthentication.Service.Dni.DniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class DniServiceTest extends IntegrationTest {


    private int index = 0;

    private List<String> dnis = Arrays.asList("86854224Z", "25108985T", "45822494P", "41038536G", "52304534G");

    @Autowired
    private DniService dniService;

    @Test
    public void checkValidDniShouldReturnTrue() throws Exception {

        /*-------------------------- Given  --------------------------*/

        String dni = dnis.get(index);

        /*-------------------------- When  --------------------------*/

        Boolean response = dniService.validateDni(dni);

        /*-------------------------- Then  --------------------------*/


        assertTrue(response instanceof Boolean);
        assertTrue(response);
        index++;
    }


    @Test(expected = IllegalArgumentException.class)
    public void checkInvalidDniShouldRaiseException() throws Exception {

        /*-------------------------- Given  --------------------------*/

        String dni = "aa";

        /*-------------------------- When  --------------------------*/

        dniService.validateDni(dni);

        /*-------------------------- Then  --------------------------*/


    }

    @Override
    protected void initializeIntegrationTest() {

    }
}
