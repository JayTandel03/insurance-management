package com.onerivet.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.onerivet.exception.BadRequestException;

@Component
public class AuthUtil {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(AuthUtil.class);

    public String getLoggedInUserEmail() {

        LOGGER.info("Fetching logged-in user email");

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtAuth)) {

            LOGGER.error("User not authenticated");

            throw new BadRequestException(
                    ErrorMessageConstants.USER_NOT_AUTHENTICATED
            );
        }

        String email =
                jwtAuth.getToken()
                        .getClaimAsString("email");

        if (email == null || email.isBlank()) {

            LOGGER.error("Email not found in token");

            throw new BadRequestException(
                    "Email not found in token"
            );
        }

        return email;
    }
}


