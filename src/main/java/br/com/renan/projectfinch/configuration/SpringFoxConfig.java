package br.com.renan.projectfinch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.renan.projectfinch.resource"))
                .paths(PathSelectors.ant("/v1/**"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        Arrays.asList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("Server Error")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(404)
                                        .message("Nao Encontrado Lanche com esse id")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Acesso Negado")
                                        .build()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Project Finch",
                "Projeto para statup de Alimentos.",
                "Avaliação Técnica",
                "",
        new Contact("Renan Peres",
                "",
                "renan.henriqu@hotmail.com"),
                "License of API",
                "",
                Collections.emptyList());
    }
}
