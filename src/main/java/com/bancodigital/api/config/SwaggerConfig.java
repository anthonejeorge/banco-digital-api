package com.bancodigital.api.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {
    	
	final public static String CONTAS_TAG = "Contas"; 
	final public static String TRANSFERENCIAS_TAG = "Transferências"; 
	
	@Bean
	public OpenAPI bancoDigitalApi() {
		return new OpenAPI()
			.info(new Info()
			.title("Banco Digital API")
			.description("API REST para transferência de valores entre contas e consulta de movimentações financeiras")
			.version("v1.0.0")
			.contact(new Contact()
				.name("Anthone Jeorge")
				.email("anthonej@gmail.com"))
			.license(new License()
				.name("MIT")));
	}

	@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/contas/**", "/transferir/**")
                .build();
    }
}
