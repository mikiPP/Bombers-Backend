package org.lasencinas.dni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(useDefaultFilters=false)
public class DniApplication {

    public static void main(String[] args) {
        SpringApplication.run(DniApplication.class, args);
    }

}
