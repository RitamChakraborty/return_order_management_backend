package dev.ritam.authorization.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.ritam.authorization.exception.InvalidJWTException;
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

public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

    @Override
    protected void doFilterInternal(
            @NonNull
                    HttpServletRequest request,
            @NonNull
                    HttpServletResponse response,
            @NonNull
                    FilterChain filterChain
    ) throws ServletException, IOException {
        if (
                !request.getServletPath().equals("/login") &&
                        !request.getServletPath().equals("/signup")
        ) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (token != null && token.startsWith("Bearer ")) {
                try {
                    token = token.replaceAll("Bearer ", "");

                    Algorithm algorithm = Algorithm.HMAC512(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String username = decodedJWT.getSubject();
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, null);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } catch (JWTVerificationException e) {
                    throw new InvalidJWTException(e.getMessage());
                }
            } else {
                throw new InvalidJWTException("Bad token");
            }
        }

        filterChain.doFilter(request, response);
    }
}
