package com.onerivet.config;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.jwt.JwtClaimValidator;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;
import com.onerivet.security.JwtAuthConverter;
import com.onerivet.util.Constants;
import com.onerivet.util.ErrorMessageConstants;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Value(Constants.OAUTH_ISSUER_URI)
	private String issuer;

	@Value(Constants.OAUTH_AUDIENCE_URI)
	private String audience;

	private final CorsConfigurationSource corsConfigurationSource;

	public SecurityConfig(CorsConfigurationSource corsConfigurationSource) {
		this.corsConfigurationSource = corsConfigurationSource;
	}

	private static String[] toArray(final List<String> urls) {
		return urls.toArray(String[]::new);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) {

		final JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();

		authenticationConverter.setJwtGrantedAuthoritiesConverter(new JwtAuthConverter());

		http.cors(cors -> cors.configurationSource(corsConfigurationSource))
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(toArray(Constants.PUBLIC_URLS)).permitAll()
						.anyRequest().authenticated())

				.oauth2ResourceServer(oauth -> oauth
						.jwt(jwt -> jwt.decoder(jwtDecoder())
								.jwtAuthenticationConverter(authenticationConverter)))
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, error) ->
								writeError(response, HttpServletResponse.SC_UNAUTHORIZED, ErrorMessageConstants.UNAUTHORIZED_MESSAGE))
						.accessDeniedHandler((request, response, error) ->
								writeError(response, HttpServletResponse.SC_FORBIDDEN, ErrorMessageConstants.FORBIDDEN_MESSAGE)));
		return http.build();
	}

	@Bean
	public NimbusJwtDecoder jwtDecoder() {
		final NimbusJwtDecoder decoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuer);

		final OAuth2TokenValidator<Jwt> issuerValidator = new JwtIssuerValidator(issuer);

		final OAuth2TokenValidator<Jwt> audienceValidator =
				new JwtClaimValidator<>(JwtClaimNames.AUD, aud -> aud != null && ((List<String>) aud).contains(audience));

		final OAuth2TokenValidator<Jwt> timestampValidator =
				new JwtTimestampValidator(Duration.ofSeconds(Constants.CLOCK_SKEW_SECONDS));

		final OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(
				List.of(issuerValidator, audienceValidator, timestampValidator));

		decoder.setJwtValidator(validator);
		return decoder;
	}

	private void writeError(final HttpServletResponse response, final int status, final String message)throws IOException {
		response.setStatus(status);
		response.setContentType(MediaType.TEXT_PLAIN_VALUE);
		response.getWriter().write(message);
	}
}
