package com.onerivet.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import com.onerivet.util.Constants;

public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	private final JwtGrantedAuthoritiesConverter roleConverter;

	public JwtAuthConverter() {
		this.roleConverter = new JwtGrantedAuthoritiesConverter();
		this.roleConverter.setAuthoritiesClaimName(Constants.ROLES_CLAIM);
		this.roleConverter.setAuthorityPrefix(Constants.ROLE_PREFIX);
	}

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {

		List<GrantedAuthority> authorities = new ArrayList<>();

		roleConverter.convert(jwt)
				.forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth.getAuthority().toUpperCase())));

		List<String> permissions = Objects.requireNonNullElse(jwt.getClaimAsStringList(Constants.PERMISSIONS_CLAIM),
				List.of());

		permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.toUpperCase())));

		return authorities;
	}
}