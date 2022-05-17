package dev.ritam.authorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.ritam.authorization.exception.InvalidJWTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");
    private static final String[] AUTH_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars",
            // Swagger UI v3
            "/v3/api-docs",
            "/swagger-ui",
            "/favicon.ico",
            // Permitted urls
            "/login",
            "/signup"
    };

    @Override
    protected void doFilterInternal(
            @NonNull
                    HttpServletRequest request,
            @NonNull
                    HttpServletResponse response,
            @NonNull
                    FilterChain filterChain
    ) throws ServletException, IOException {
        String requestUrl = request.getServletPath();
        boolean whiteListedRequest = false;

        for (String url : AUTH_WHITELIST) {
            if (requestUrl.startsWith(url)) {
                whiteListedRequest = true;
                break;
            }
        }

        if (!whiteListedRequest) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (token != null && token.startsWith("Bearer ")) {
                try {
                    token = token.replace("Bearer ", "");

                    var algorithm = Algorithm.HMAC512(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    var decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    var usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } catch (JWTVerificationException e) {
                    var errorMsg = String.format(
                            "AuthenticationFilter.doFilterInternal " +
                                    "(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) : " +
                                    "%s",
                            e.getMessage()
                    );
                    log.error(errorMsg);
                    throw new InvalidJWTException(errorMsg);
                }
            } else {
                var errorMsg = "AuthenticationFilter.doFilterInternal " +
                        "(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) : " +
                        "Bad token";
                log.error(errorMsg);
                throw new InvalidJWTException(errorMsg);
            }
        }

        filterChain.doFilter(request, response);
    }
}
