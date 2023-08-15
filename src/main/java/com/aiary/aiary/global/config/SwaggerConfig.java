package com.aiary.aiary.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.Collections;

// swagger 접속 url -> http://localhost:8080/swagger-ui/index.html#/

@Configuration
@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", apiKeySecuritySchema()))
                        .security(Collections.singletonList(schemaRequirement))
                .info(new Info().title("AIARY API")
                        .description("사용자가 작성한 일기에서 키워드를 추출하여 그림일기 그려주는 서비스")
                        .version("v2"));
    }

    public SecurityScheme apiKeySecuritySchema() {
        return new SecurityScheme()
                .name("Authorization")
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP);
    }

    SecurityRequirement schemaRequirement = new SecurityRequirement().addList("bearerAuth");
}
