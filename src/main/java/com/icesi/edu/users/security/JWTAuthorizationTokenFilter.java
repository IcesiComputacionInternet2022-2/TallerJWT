package com.icesi.edu.users.security;

import com.icesi.edu.users.constant.UserErrorCode;
import com.icesi.edu.users.error.exception.UserError;
import com.icesi.edu.users.error.exception.UserException;
import com.icesi.edu.users.utils.JWTParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.MalformedJwtException;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@Order(1)
public class JWTAuthorizationTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authentication";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String USER_ID_CLAIM_NAME = "userId";
    private static final String[] EXCLUDE_PATHS = {"POST /login"};

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try {
                if(containsToken(request)){
                    String jwtToken = request.getHeader(AUTHORIZATION_HEADER.replace(TOKEN_PREFIX, StringUtils.EMPTY));
                    Claims claims = JWTParser.decodeJWT(jwtToken);
                    SecurityContext context = parseClaims(jwtToken, claims);
                    SecurityContextHolder.setUserContext(context);
                    filterChain.doFilter(request, response);
                }
                   else{
                    //Since this part it's executed before the handler exception, the program crashes, so i decided to establish the response by code.

//                  throw new UserException(HttpStatus.UNAUTHORIZED, new UserError(UserErrorCode.CODE_401, UserErrorCode.CODE_401.getMessage()));
                    response.setStatus(401);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);
                    response.getWriter().write(UserErrorCode.CODE_401.getMessage());
                    response.getWriter().flush();
                }


            } catch ( JwtException e){
                System.out.println(e.getClass());
                System.out.println("Error verifying jwt token: " + e.getMessage());
            } finally {
                SecurityContextHolder.clearContext();
            }
    }


    private boolean containsToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(AUTHORIZATION_HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(TOKEN_PREFIX);
    }

    private String claimKey(Claims claims, String key) {
        String value = (String)claims.get(key);
        return Optional.ofNullable(value).orElseThrow();
    }
    public SecurityContext parseClaims(String jwtToken, Claims claims) {
        String userId = claimKey(claims, USER_ID_CLAIM_NAME);
        SecurityContext context = new SecurityContext();
        try{
            context.setUserId(UUID.fromString(userId));
            context.setToken(jwtToken);
        }catch (IllegalArgumentException e){
            throw new MalformedJwtException("Error parsing jwt");
        }

        return context;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String methodPlusPaths = request.getMethod() + " " + request.getRequestURI();
        return Arrays.stream(EXCLUDE_PATHS).anyMatch(path -> path.equalsIgnoreCase(methodPlusPaths));
    }
}
