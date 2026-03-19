package com.fiap.infomed.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI InfoMed() {
        return new OpenAPI().info(
                new Info().title("InfoMed API")
                        .description("Projeto desenvolvido durante a fase 3 do curso FIAP")
                        .version("v0.0.2")
                        .license(new License().name("Apache 2.0").url("https://github.com/blbernat/InfoMed.git")));
    }

    @Bean
    public GroupedOpenApi apiV1() {
        return GroupedOpenApi.builder()
                .group("api-v1")
                .pathsToMatch("/api/**")
                .build();
    }
}
