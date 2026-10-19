package com.onerivet.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.onerivet.util.Constants;

@Configuration
public class CorsConfig {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {

		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of(Constants.ALL));

		configuration.setAllowedMethods(Constants.ALLOWED_METHODS);

		configuration.setAllowedHeaders(Constants.ALLOWED_HEADERS);

		configuration.setAllowCredentials(true);

		configuration.setMaxAge(Constants.MAX_AGE_SECS);

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(Constants.ALL_PATHS, configuration);

		return source;
	}
}