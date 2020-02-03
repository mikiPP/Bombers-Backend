package org.lasencinas.dni.Api;


import org.lasencinas.dni.Model.Api.DniDto;
import org.lasencinas.dni.Service.DniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public DniDto updateDni(@RequestParam String dni, @RequestParam Long id) {
        return dniService.updateDni(dni,id);
    }


}
