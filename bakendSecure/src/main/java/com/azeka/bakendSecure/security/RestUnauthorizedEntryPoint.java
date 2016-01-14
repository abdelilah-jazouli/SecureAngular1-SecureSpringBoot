package com.azeka.bakendSecure.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;


/**
 * Override the AuthenticationEntryPoint to always return the code 401 UNAUTHORIZED. 
 * This will override the default behavior of Spring Security which is forwarding the user 
 * to the login page if he don’t meet the security requirements, because on REST we don’t have any login page ;
 * Returns a 401 error code (Unauthorized) to the client.
 */
@Component
public class RestUnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(RestUnauthorizedEntryPoint.class);

    /**
     * Always returns a 401 error code to the client.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
        throws IOException,
        ServletException {

        log.debug("Pre-authenticated entry point called. Rejecting access");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
