package org.lasencinas.bombersauthentication.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.bombersauthentication.IntegrationTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DniControllerTest extends IntegrationTest {


    private int index = 0;

    private List<String> dnis = Arrays.asList("86854224Z", "25108985T", "45822494P", "41038536G", "52304534G");

    @Test
    public void checkValidDniShouldReturnTrue() throws Exception {

        /*-------------------------- Given  --------------------------*/


        /*-------------------------- When  --------------------------*/

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/dni?dni=" + dnis.get(index))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Boolean response = super.mapFromJson(content, Boolean.class);


        /*-------------------------- Then  --------------------------*/

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertTrue(response instanceof Boolean);
        assertTrue(response);
        index++;
    }


    @Test
    public void checkInvalidDniShouldReturnBadRequest() throws Exception {

        /*-------------------------- Given  --------------------------*/




        /*-------------------------- When  --------------------------*/

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/dni?aaa")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        /*-------------------------- Then  --------------------------*/

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

    }

    @Override
    protected void initializeIntegrationTest() {

    }
}
