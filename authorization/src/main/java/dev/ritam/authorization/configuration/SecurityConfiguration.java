package dev.ritam.authorization.configuration;

import dev.ritam.authorization.filter.AuthenticationFilter;
import dev.ritam.authorization.filter.AuthorizationFilter;
import dev.ritam.authorization.filter.ExceptionHandlerFilter;
import dev.ritam.authorization.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            // Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // Swagger UI v3
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // Actuator
            "/actuator/**",
            // Permitted urls
            "/authorization/api/login",
            "/authorization/api/signup",
            "/authorization/api/docs"
    };
    private static final String LOGIN_URL = "/authorization/api/login";
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final PropertyValuesConfiguration propertyValuesConfiguration;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerService)
                .passwordEncoder(passwordEncoder);
    }

    private AuthorizationFilter authorizationFilter() throws Exception {
        AuthorizationFilter authorizationFilter = new AuthorizationFilter(authenticationManagerBean(), propertyValuesConfiguration);
        authorizationFilter.setFilterProcessesUrl(LOGIN_URL);
        return authorizationFilter;
    }

    private AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter(propertyValuesConfiguration);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authorizationFilter())
                .addFilterBefore(exceptionHandlerFilter, LogoutFilter.class)
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
