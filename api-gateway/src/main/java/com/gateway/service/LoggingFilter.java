package com.gateway.service;

//import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Component
public class LoggingFilter implements GlobalFilter{

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @SuppressWarnings("null")
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Set<URI> uris = exchange.getAttributeOrDefault(GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
        String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        URI routeUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);

        // Log request details (e.g., URL, method, headers)
        logger.info("Incoming request: {} {}, is routed to id {}, uri: {}", exchange.getRequest().getMethod(), originalUri, route.getId(), routeUri);
        logger.info("Request headers: {}", exchange.getRequest().getHeaders());

        // Log client IP address
        logger.info("Client IP address: {}", exchange.getRequest().getRemoteAddress().getAddress());


        // Continue the filter chain
        return chain.filter(exchange).doFinally(signalType -> {
            // Log response details (e.g., status code)
            logger.info("Response status code: {}", exchange.getResponse().getStatusCode());
        });
    

    }
    
}
