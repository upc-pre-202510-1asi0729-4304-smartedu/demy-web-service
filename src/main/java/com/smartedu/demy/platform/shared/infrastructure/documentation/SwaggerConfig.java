package com.smartedu.demy.platform.shared.infrastructure.documentation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://demy-web-service-production.up.railway.app") // usa HTTPS
                ))
                .info(new Info()
                        .title("Demy API")
                        .version("1.0.0")
                        .description("API for Demy platform"));
    }
}
