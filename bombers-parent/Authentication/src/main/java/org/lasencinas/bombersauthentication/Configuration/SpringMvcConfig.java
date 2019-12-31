package org.lasencinas.bombersauthentication.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.swagger2.mappers.ModelMapper;

@Configuration
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"};
    private final ModelMapper modelMapper;
    @Value(value = "*")
    private String allowedOrigin;
    @Value("${swagger.enabled}")
    private boolean swaggerEnabled;

    @Autowired
    public SpringMvcConfig(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins(allowedOrigin)
                .allowedOrigins("http://localhost:4200");
    }

    /**
     * Enables Swagger /swagger-ui.html resource.
     *
     * @param registry ResourceHandlerRegistry.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);

        if (swaggerEnabled) {

            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/index.html");
    }
}