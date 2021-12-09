package com.example.ProiectDistributedSystems.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@EnableScheduling
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    static final String MESSAGE_PREFIX = "/topic";

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        System.out.println("configureMessageBroker : " + registry);

        registry.enableSimpleBroker(MESSAGE_PREFIX);
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("configureMessageBroker : " + registry);
        registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws-message").setAllowedOriginPatterns("*").withSockJS();
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();


//        registry.addEndpoint("/socket").setAllowedOrigins("http://localhost:00").withSockJS();
    }


}