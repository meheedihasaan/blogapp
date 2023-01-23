package com.blog.app.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Book App",
                version = "1.0",
                contact = @Contact(
                        name = "Md Mehedi Hasan",
                        email = "mehedihasan111941@gmail.com",
                        url = "https://github.com/meheedihasaan"
                ),
                license = @License(
                        name = "N/A"
                ),
                termsOfService = "N/A",
                description = "This project is developed by Md Mehedi Hasan"
        ),
        servers = @Server(
                url = "https://community.render.com/",
                description
                        = "Local Server"
        )
)
public class OpenApi30Config {

    //To add global JWT Authentication in Swagger Open APIs
    @Bean
    public OpenAPI openAPISecurityConfig() {
        final String SECURITY_SCHEME_NAME = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }

}
