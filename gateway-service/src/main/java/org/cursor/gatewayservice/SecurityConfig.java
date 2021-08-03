package org.cursor.gatewayservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.jwt.secret}")
    private String secret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                (request, response, e) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(
                        new JwtTokenAuthenticationFilter(secret),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()

                .antMatchers("/auth-service/*").anonymous()

                .antMatchers("/account-service/accounts/notifications/*").permitAll()
                .antMatchers("/account-service/accounts/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/shop-service/**").permitAll()
                .antMatchers(HttpMethod.POST, "/shop-service/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/shop-service/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/shop-service/**").hasRole("ADMIN")

                .antMatchers("/statistic-service/**").hasRole("ADMIN")

                .antMatchers("/eureka/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
