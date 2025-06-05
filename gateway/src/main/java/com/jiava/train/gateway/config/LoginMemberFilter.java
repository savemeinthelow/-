package com.jiava.train.gateway.config;

import com.jiava.train.gateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoginMemberFilter implements Ordered,GlobalFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginMemberFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        LOG.info("过滤器");
        String path = exchange.getRequest().getURI().getPath();
        if (path.contains("/admin")
                ||path.contains("/member/member/login")
                ||path.contains("/member/member/send-code")
                ||path.contains("/business")
                ||path.contains("/business/kaptcha")
        ){
            LOG.info("不需要登录验证: {}",path);
            return chain.filter(exchange);
        }
        else {
            LOG.info("需要登录验证: {}",path);
        }
        String token = exchange.getRequest().getHeaders().getFirst("token");
        LOG.info("开始验证会员,token:{}",token);
        if (token==null||token.isEmpty()){
            LOG.info("token为空，请求被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        if (JwtUtil.validate(token)){
            LOG.info("token有效，放行");
            return chain.filter(exchange);
        }
        else{
            LOG.info("token无效，被拦截");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
