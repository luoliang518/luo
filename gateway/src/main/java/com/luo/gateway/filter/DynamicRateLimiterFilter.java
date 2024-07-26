package com.luo.gateway.filter;

import com.luo.common.constant.TokenConstant;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class DynamicRateLimiterFilter implements GatewayFilter , Ordered {

    private final RequestRateLimiterGatewayFilterFactory rateLimiterFactory;

    public DynamicRateLimiterFilter(RequestRateLimiterGatewayFilterFactory rateLimiterFactory) {
        this.rateLimiterFactory = rateLimiterFactory;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String auth = exchange.getRequest().getQueryParams().getFirst(TokenConstant.AUTH);
        RequestRateLimiterGatewayFilterFactory.Config config = new RequestRateLimiterGatewayFilterFactory.Config();
        // 根据userid设置不同的限流配置
        RedisRateLimiter redisRateLimiter;
        if (auth != null) {
            redisRateLimiter = new RedisRateLimiter(10, 20);
        } else {
            redisRateLimiter = new RedisRateLimiter(1, 1);
        }
        config.setRateLimiter(redisRateLimiter);
        GatewayFilter rateLimiterFilter = rateLimiterFactory.apply(config);
        return rateLimiterFilter.filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
