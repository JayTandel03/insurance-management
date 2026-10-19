package com.onerivet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.onerivet.util.Constants;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(Constants.BEARER_AUTHENTICATION))
				.components(new Components()
						.addSecuritySchemes(Constants.BEARER_AUTHENTICATION, new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme(Constants.BEARER)
								.bearerFormat(Constants.JWT)));
	}
	
} 