package com.veterinary.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
            return new OpenAPI()
                    .info(new Info()
                            .title("Veteriner Yönetim Sistemi API Dokümantasyonu")
                            .version("1.0")
                            .description("Bu dokümantasyon, Veteriner Klinik Yönetimi için oluşturulan RESTful servisleri içerir."))
                    .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                    .components(new Components().addSecuritySchemes("bearerAuth",
                            new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                                    .description("JWT Token değerini giriniz.")));
    }
}
