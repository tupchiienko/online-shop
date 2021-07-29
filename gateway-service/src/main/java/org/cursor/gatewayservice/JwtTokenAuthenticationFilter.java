package org.cursor.gatewayservice;

import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final String secret;

    public JwtTokenAuthenticationFilter(String secret) {
        this.secret = secret;
    }

    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            try {
                var claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
                var username = claims.getSubject();
                @SuppressWarnings("unchecked")
                List<String> authorities = claims.get("authorities", List.class);
                if (username != null) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception ignore) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
