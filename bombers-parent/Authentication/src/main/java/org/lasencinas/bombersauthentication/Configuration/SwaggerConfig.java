package org.lasencinas.bombersauthentication.Configuration;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import org.lasencinas.bombersauthentication.Model.Domain.AuthUser.AuthUser;
import org.lasencinas.bombersauthentication.Model.Domain.Dni.Dni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.Link;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .additionalModels(typeResolver.resolve(AuthUser.class), typeResolver.resolve(Dni.class))
                .globalOperationParameters(apiParameters())
                .ignoredParameterTypes(Link.class, ModelAndView.class,
                        WebMvcProperties.View.class, Map.class);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Fight Fighter REST API")
                .description("Fight fighter REST API DESCRIPTION")
                .version("1.0")
                .license("mpp © Copyright 2020")
                .build();
    }

    private List<Parameter> apiParameters() {
        return Collections.singletonList(new ParameterBuilder()
                .name("Authentication API")
                .description("Header API")
                .modelRef(new ModelRef("header"))
                .parameterType("string")
                .required(false)
                .build());
    }
}
