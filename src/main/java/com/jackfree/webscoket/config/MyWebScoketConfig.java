package com.jackfree.webscoket.config;

import com.jackfree.webscoket.chat.MyWebSocketHandler;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author huijie.deng
 * @CreateDate: 2018/10/22
 */
@Configuration
@EnableWebSocket
@EnableAutoConfiguration
public class MyWebScoketConfig  implements WebSocketConfigurer{

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getMyWebSocketHandler(),"/chat")
                .addInterceptors(new HttpSessionHandshakeInterceptor(){
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                HttpServletRequest httpServletRequest = servletRequest.getServletRequest();

                String mid = "1";//httpServletRequest.getHeader("mid");
                if(!StringUtils.isEmpty(mid)){
                    attributes.put("mid",Long.valueOf(mid));
                    return true;
                }
                return false;
            }

        }).setAllowedOrigins("*");
    }
    @Bean
    public MyWebSocketHandler getMyWebSocketHandler(){
        return new MyWebSocketHandler();
    }
}
