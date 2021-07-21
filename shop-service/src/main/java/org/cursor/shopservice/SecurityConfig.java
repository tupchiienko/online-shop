package org.cursor.shopservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/categories/*").permitAll()
                .antMatchers(HttpMethod.POST, "/categories/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/categories/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/categories/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/positions/*").permitAll()
                .antMatchers(HttpMethod.POST, "/positions/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/positions/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/positions/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

}
