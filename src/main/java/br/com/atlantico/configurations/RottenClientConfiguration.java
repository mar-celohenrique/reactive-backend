package br.com.atlantico.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class RottenClientConfiguration {

    @Value("https://www.rottentomatoes.com/api/private/v1.0/movies")
    private String url;

    @Value("40")
    private Long timeout;

    @Value("3")
    private Long maxRetries;

    public WebClient getRottenClient() {
        return WebClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "text/javascript")
                .baseUrl(this.url)
                .filter(this.logRequest())
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return (request, next) -> {
            log.info("Request: {} {}", request.method(), request.url());
            request.headers()
                    .forEach((name, values) -> values.forEach(value -> log.info("{} = {}", name, value)));
            return next.exchange(request);
        };
    }

    public Long getTimeout() {
        return this.timeout;
    }

    public Long getMaxRetries() {
        return this.maxRetries;
    }

}
