package me.tvhook.tvwebhook.common.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    public WebClient getWebClient() {

        // Timeout config (HttpClient)
        HttpClient httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofMillis(5000))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            );

        // increase maxInMemorySize (default : 256KB)
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
            .codecs(configurer ->
                configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50))
            .build();

        // logging level DEBUG
        exchangeStrategies.messageWriters().stream().filter(LoggingCodecSupport.class::isInstance)
            .forEach(writer ->
                ((LoggingCodecSupport) writer).setEnableLoggingRequestDetails(true)
            );

        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("https://api.upbit.com/v1/")
//            .defaultCookie("cookieKey", "cookieValue")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT_CHARSET, String.valueOf(StandardCharsets.UTF_8))
            .filter(ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    log.debug("Request : {} {}", clientRequest.method(), clientRequest.url());
                    clientRequest.headers().forEach((name, values) -> values
                        .forEach((value) -> log.debug("{} : {}", name, value)));
                    return Mono.just(clientRequest);
                }
            ))
            .filter(ExchangeFilterFunction.ofResponseProcessor(
                clientResponse -> {
                    clientResponse.headers().asHttpHeaders().forEach((name, values) -> values
                        .forEach(value -> log.debug("{} : {}", name, value)));
                    return Mono.just(clientResponse);
                }
            ))
            .build();

    }


}
