package com.study.party.config.swagger;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30).securityContexts(Arrays.asList(securityContext()))
                                                   .securitySchemes(Arrays.asList(apiKey()))
                                                   .select()
                                                   .apis(RequestHandlerSelectors.basePackage("com.study.party"))
                                                   .paths(PathSelectors.any())
                                                   .build()
                                                   .groupName("all")
                                                   .useDefaultResponseMessages(false)
                                                   .apiInfo(apiInfo());
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Lunch Room Study [Party]")
                                   .description("Lunch Room Study [Party] Swagger")
                                   .version("1.0")
                                   .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

}
