package com.team.ms.teammsgateway;

import org.springframework.cloud.gateway.event.RefreshRoutesResultEvent;
import org.springframework.cloud.gateway.route.CachingRouteLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralGatewayConfig {


    private String API_KEY;
    private String API_SECRET;

    @Bean
    RouteLocator gateway(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r ->
                        r
                                .path("/api/**")
//                                .filters(f -> f.addRequestHeader( ))
                                .uri("lb://main/"))
                .build();
    }

    @Bean
    public ApplicationListener<RefreshRoutesResultEvent> routesResultEvent() {
        return GeneralGatewayConfig::onApplicationEvent;
    }


    private static void onApplicationEvent(RefreshRoutesResultEvent event) {
        CachingRouteLocator x;
        x = (CachingRouteLocator) event.getSource();
        x.getRoutes().subscribe(System.out::println);
    }

}
