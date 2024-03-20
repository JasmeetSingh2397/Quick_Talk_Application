package com.example.quick_talk.configs;

import com.example.quick_talk.models.ChatMessage;
import com.example.quick_talk.models.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.swing.*;

// This class is annotated with @Component, which indicates that it is a Spring-managed component
// and will be automatically detected during component scanning.
// It uses Lombok annotations such as @Slf4j for logging and @RequiredArgsConstructor to
// automatically generate a constructor injecting dependencies.
// It imports necessary classes including ChatMessage, MessageType, SimpMessageSendingOperations,
// StompHeaderAccessor, and SessionDisconnectEvent.
@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

//    The @EventListener annotation indicates that the handleWebSocketDisconnectListener method
//    is an event listener for Spring application events.
//    It listens specifically for SessionDisconnectEvent, which is triggered when a WebSocket
//    session is disconnected.
//    It extracts the username attribute from the session attributes, which is previously set
//    during WebSocket connection establishment. This allows the system to identify the user who is
//    disconnecting.
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

//    If the username is not null (indicating that a user is indeed disconnecting), it logs the disconnection event.
//    It then creates a ChatMessage object representing the user's leave event, specifying the message type as LEAVE and the
//    sender as the disconnected user's username.
//    Finally, it uses messagingTemplate (an instance of SimpMessageSendingOperations) to broadcast
//    the leave message to all subscribed clients by sending it to the /topic/public destination.

}
