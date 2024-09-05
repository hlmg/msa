package hlmg.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class GlobalFilterConfig {

    @Bean
    public GlobalFilter filter() {
        return (exchange, chain) -> {
            log.info("Pre-filter is executed...");

            String requestPath = exchange.getRequest().getPath().toString();
            log.info("Request path={}", requestPath);

            HttpHeaders headers = exchange.getRequest().getHeaders();
            headers.forEach((key, value) -> log.info("{} {}", key, value));

            return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("Post-filter is executed...")));
        };
    }
}
