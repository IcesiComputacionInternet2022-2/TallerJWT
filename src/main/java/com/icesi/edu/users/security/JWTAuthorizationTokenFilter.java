package com.icesi.edu.users.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icesi.edu.users.constant.UserDemoErrorCode;
import com.icesi.edu.users.error.exception.UserDemoError;
import com.icesi.edu.users.error.exception.UserDemoException;
import com.icesi.edu.users.utils.JWTParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;


@Component
@Order(1)
public class JWTAuthorizationTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String USER_ID_CLAIM_NAME = "userId";

    private static final String[] excludedPaths = {"POST /login"};


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (containsToken(request)) {
                String jwtToken = request.getHeader(AUTHORIZATION_HEADER).replace(TOKEN_PREFIX, StringUtils.EMPTY);
                Claims claims = JWTParser.decodeJWT(jwtToken);
                SecurityContext context = parseClaims(jwtToken, claims);
                SecurityContextHolder.setUserContext(context);
                if(getUserFilter(request, response))
                    filterChain.doFilter(request, response);
            } else {
                createUnauthorizedFilter(new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserDemoErrorCode.CODE_01, UserDemoErrorCode.CODE_01.getMessage())), response);
            }
        } catch (JwtException e) {
            createUnauthorizedFilter(new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserDemoErrorCode.CODE_01, UserDemoErrorCode.CODE_01.getMessage())), response);
        } finally {
            SecurityContextHolder.clearContext();
        }
    }

    private SecurityContext parseClaims(String jwtToken, Claims claims) {
        String userId = claimKey(claims, USER_ID_CLAIM_NAME);

        SecurityContext context = new SecurityContext();
        try {
            context.setUserId(UUID.fromString(userId));
            context.setToken(jwtToken);
        } catch (IllegalArgumentException e) {
            throw new MalformedJwtException("Error parsing jwt");
        }
        return context;
    }

    private String claimKey(Claims claims, String key) {
        String value = (String) claims.get(key);
        return Optional.ofNullable(value).orElseThrow();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String methodPlusPath = request.getMethod() + " " + request.getRequestURI();
        return Arrays.stream(excludedPaths).anyMatch(path -> path.equalsIgnoreCase(methodPlusPath));
    }

    protected boolean getUserFilter(HttpServletRequest req, HttpServletResponse res) {
        boolean authorized = true;
        String method = req.getMethod();
        String path = req.getRequestURI();

        if (method.equals("GET") && path.startsWith("/users/") && !path.endsWith(SecurityContextHolder.getContext().getUserId().toString())) {
            createUnauthorizedFilter(new UserDemoException(HttpStatus.UNAUTHORIZED, new UserDemoError(UserDemoErrorCode.CODE_02, UserDemoErrorCode.CODE_02.getMessage())), res);
            authorized = false;
        }

        return authorized;
    }

    private boolean containsToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(AUTHORIZATION_HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(TOKEN_PREFIX);
    }

    @SneakyThrows
    private void createUnauthorizedFilter(UserDemoException userDemoException, HttpServletResponse response) {

        ObjectMapper objectMapper = new ObjectMapper();

        UserDemoError userDemoError = userDemoException.getError();

        String message = objectMapper.writeValueAsString(userDemoError);

        response.setStatus(401);
        response.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
        response.getWriter().flush();
    }
}
