package com.team.ms.teammsgateway.security.filter;


import com.tms.common.security.AppUserDetailsService;
import com.tms.common.security.service.JWTService;
import com.tms.common.userAuthDataConfiguration.AppUserDetails;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Primary
@Component
public class JWTAuthFilter implements GatewayFilter {

    @Autowired
    private JWTService jwtTokenService;
    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Override
    @SneakyThrows
    @ResponseStatus(HttpStatus.UNAUTHORIZED)  // 403
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();
        if (request.getURI().getPath().endsWith("/login") || request.getURI().getPath().endsWith("/logout")) {
            return chain.filter(exchange);
        } else {
            final String token = jwtTokenService.getTokenFromRequest(request);

            if (jwtTokenService.isValid(token)) {
                AppUserDetails appUserDetails = (AppUserDetails) appUserDetailsService.loadUserByUsername(jwtTokenService.getPrincipal(token));
                UsernamePasswordAuthenticationToken t =
                        new UsernamePasswordAuthenticationToken(appUserDetails, null, null);
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(t);
                return chain.filter(exchange);
            } else {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }
    }
}
