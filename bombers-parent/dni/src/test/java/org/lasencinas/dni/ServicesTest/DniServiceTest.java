package org.lasencinas.dni.ServicesTest;


import com.mpp.commons.test.IntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.dni.Model.Api.DniDto;
import org.lasencinas.dni.Service.DniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


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


        Assert.assertTrue(response instanceof Boolean);
        Assert.assertTrue(response);
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

    @Test
    public void updateDniWithValidOneShouldReturnDniDto() {


        /*-------------------------- Given  --------------------------*/

        String dni = "1234567-F";
        Long id = 3L;

        /*-------------------------- When  --------------------------*/

        DniDto dniUpdated = dniService.updateDni(dni, id);

        /*-------------------------- Then  --------------------------*/

        Assert.assertTrue(dniUpdated.getBomberId() != 0);
        Assert.assertEquals(dniUpdated.getDni(), dni);
        index++;
    }


    @Test(expected = IllegalArgumentException.class)
    public void updateDniWithInvalidDniShouldRaiseException() {


        /*-------------------------- Given  --------------------------*/

        String dni = "41038536G";
        Long id = 5L;
        /*-------------------------- When  --------------------------*/

        DniDto dniUpdated = dniService.updateDni(dni, id);

        /*-------------------------- Then  --------------------------*/


    }

    @Override
    protected void initializeIntegrationTest() {

    }
}
