package org.lasencinas.bombersauthentication.IntegrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lasencinas.bombersauthentication.IntegrationTest;
import org.lasencinas.bombersauthentication.Model.Api.AuthUserDto;
import org.lasencinas.bombersauthentication.Model.Api.DniDto;
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
public class AuthUserControllerTest extends IntegrationTest {

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

        AuthUserDto authUserDto = super.mapFromJson(content, AuthUserDto.class);


        /*-------------------------- Then  --------------------------*/

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertTrue(authUserDto instanceof AuthUserDto);
    }


    @Test
    public void createAAuthUserWithBadBodyShouldReturnBadRequest() throws Exception {

        /*-------------------------- Given  --------------------------*/

        AuthUserDto authUserDto = createAuthUserDto();
        authUserDto.setDni(null);

        String inputJson = super.mapToJson(authUserDto);


        /*-------------------------- When  --------------------------*/

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();


        /*-------------------------- Then  --------------------------*/

        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());

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
