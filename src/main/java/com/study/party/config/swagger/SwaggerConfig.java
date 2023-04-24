package com.study.party.config.swagger;

import com.study.party.auth.vo.CustomUserDetailsVo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.*;
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
        List<Response> defaultResponses = Arrays.asList(
                new ResponseBuilder().code("200").description("OK").build(),
                new ResponseBuilder().code("400").description("BAD Request").build(),
                new ResponseBuilder().code("404").description("NOT FOUND").build(),
                new ResponseBuilder().code("500").description("INTERNAL SERVER ERROR").build()
        );

        return new Docket(DocumentationType.OAS_30).securityContexts(Arrays.asList(securityContext()))
                                                   .securitySchemes(Arrays.asList(apiKey()))
                                                   .globalResponses(HttpMethod.GET, defaultResponses)
                                                   .globalResponses(HttpMethod.POST, defaultResponses)
                                                   .globalResponses(HttpMethod.PUT, defaultResponses)
                                                   .globalResponses(HttpMethod.DELETE, defaultResponses)
                                                   .ignoredParameterTypes(CustomUserDetailsVo.class)
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
