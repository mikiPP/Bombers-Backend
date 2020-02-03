package org.lasencinas.bombersauthentication.IntegrationTests;


import com.mpp.commons.test.IntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.bombersauthentication.Model.Api.BomberDto;
import org.lasencinas.dni.Model.Api.DniDto;
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
public class BomberControllerTest extends IntegrationTest {

    private Long Id = 1l;

    private int index = 0;

    private List<String> dnis = Arrays.asList("86854224Z", "25108985T", "45822494P", "41038536G", "52304534G");

    @Test
    public void createAAuthUserShouldReturnTheSameAuthUser() throws Exception {

        /*-------------------------- Given  --------------------------*/

        String inputJson = super.mapToJson(createAuthUserDto());

        /*-------------------------- When  --------------------------*/

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        BomberDto bomberDto = super.mapFromJson(content, BomberDto.class);


        /*-------------------------- Then  --------------------------*/

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertTrue(bomberDto instanceof BomberDto);
    }


    @Test
    public void createAAuthUserWithBadBodyShouldReturnBadRequest() throws Exception {

        /*-------------------------- Given  --------------------------*/

        BomberDto bomberDto = createAuthUserDto();
        bomberDto.setDni(null);

        String inputJson = super.mapToJson(bomberDto);


        /*-------------------------- When  --------------------------*/

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();


        /*-------------------------- Then  --------------------------*/

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

    }


    private BomberDto createAuthUserDto() {

        BomberDto bomberDto = new BomberDto();

        bomberDto.setId(Id);
        bomberDto.setEmail(String.format("test%s@test.com", Id));
        bomberDto.setDni(dnis.get(index));
        bomberDto.setPassword("Test" + Id);

        Id++;
        index++;

        return bomberDto;
    }


    @Override
    protected void initializeIntegrationTest() {

    }
}
