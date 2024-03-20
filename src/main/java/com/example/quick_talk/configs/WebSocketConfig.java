package com.example.quick_talk.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.net.http.WebSocket;

//    @EnableWebSocketMessageBroker enables WebSocket message handling, with higher-level messaging protocols
//    built on top of WebSocket, such as STOMP (Simple Text Oriented Messaging Protocol).
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


//   registerStompEndpoints method configures the WebSocket endpoints. In this case,
//   it registers a STOMP endpoint /ws and enables SockJS fallback options.
//   SockJS provides a WebSocket-like object in the browser when WebSockets are not available.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();

    }

//    configureMessageBroker method configures the message broker.
//    setApplicationDestinationPrefixes sets the prefix for messages that are bound for methods annotated with @MessageMapping.
//    In this case, messages sent to destinations prefixed with /app will be routed to message-handling methods in the application.
//    enableSimpleBroker enables a simple in-memory message broker to carry messages back to the client on destinations prefixed with /topic.
//    Clients can subscribe to these topics to receive updates from the server.

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");

    }
}

