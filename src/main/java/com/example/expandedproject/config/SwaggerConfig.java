package com.example.expandedproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("All")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.expandedproject"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket memberApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Member")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.expandedproject.user"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Project - ExpandedProject 240104")
                .description("Hanwha_SW CAMP 2기 - 배운 기능 모두 적용하는 실습 프로젝트")
                .version("1.0.0")
                .build();
    }
}