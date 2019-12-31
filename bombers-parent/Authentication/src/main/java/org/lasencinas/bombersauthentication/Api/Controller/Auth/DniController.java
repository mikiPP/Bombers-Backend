package org.lasencinas.bombersauthentication.Api.Controller.Auth;


import org.lasencinas.bombersauthentication.Service.Dni.DniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(DniController.API_DNI_PATH)
public class DniController {

    static final String API_DNI_PATH = "/dni";

    private final DniService dniService;

    @Autowired
    DniController(DniService dniService) {
        this.dniService = dniService;
    }


    @GetMapping
    public Boolean validateDni(@RequestParam String dni) {
        return dniService.validateDni(dni);
    }


}
