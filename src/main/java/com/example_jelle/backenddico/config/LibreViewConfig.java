// Configuration for the LibreView API client.
package com.example_jelle.backenddico.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class LibreViewConfig {

    private static final Logger logger = LoggerFactory.getLogger(LibreViewConfig.class);

    @Value("${libreview.baseUrl:https://api-eu.libreview.io}")
    private String baseUrl;

    @Value("${libreview.product:llu.android}")
    private String product;

    @Value("${libreview.version:4.16.0}")
    private String version;

    // Creates a WebClient bean for the LibreView API.
    @Bean
    public WebClient libreViewClient(WebClient.Builder builder) {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(15));

        return builder
            .baseUrl(baseUrl)
            .defaultHeaders(headers -> {
                headers.set("product", product);
                headers.set("version", version);
                headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            })
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .filter(logRequest())
            .filter(logResponse())
            .build();
    }

    // Logs the request.
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(req -> {
            logger.info("[LibreView Request] {} {}", req.method(), req.url());
            req.headers().forEach((name, values) -> values.forEach(value -> logger.trace("  {}: {}", name, value)));
            return Mono.just(req);
        });
    }

    // Logs the response.
    private ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(res -> {
            logger.info("[LibreView Response] Status: {}", res.statusCode());
            return Mono.just(res);
        });
    }
}
