package com.donkeys_today.server.support.swagger.config;

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
      String jwt = "JWT";
      SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
      Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
          .name(jwt)
          .type(SecurityScheme.Type.HTTP)
          .scheme("bearer")
          .bearerFormat("JWT")
      );
    return new OpenAPI()
        .components(new Components())
        .info(apiInfo());
  }

  private Info apiInfo(){
    return new Info()
        .title("Clody API 문서")
        .description("Clody API ")
        .version("1.0");
  }
}
