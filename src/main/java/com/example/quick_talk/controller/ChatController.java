package com.example.quick_talk.controller;

import com.example.quick_talk.configs.WebSocketEventListener;
import com.example.quick_talk.models.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.net.http.WebSocket;

//This class is annotated with @Controller, indicating that it is a Spring MVC controller responsible
// for handling incoming HTTP requests and WebSocket messages.
@Controller
public class ChatController {

//  @MessageMapping("/chat.sendMessage"): This annotation maps WebSocket messages with the
//  destination /chat.sendMessage to this method. When a message with this destination is received
//  from a client, this method will be invoked.
//  @SendTo("/topic/public"): This annotation specifies the destination to which the return value of
//  this method will be sent. In this case, it sends the message to all subscribers of the
//  /topic/public destination.
//  public ChatMessage sendMessage(@Payload ChatMessage chatMessage): This method is responsible for
//  handling messages sent by clients. It takes a ChatMessage object as a parameter, which represents
//  the message sent by the client. It simply returns the received ChatMessage, which will then be
//  broadcasted to all clients subscribed to /topic/public.
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;

    }

//    @MessageMapping("/chat.addUser"): Similar to the previous method, this annotation maps
//    WebSocket messages with the destination /chat.addUser to this method.
//    @SendTo("/topic/public"): Again, this annotation specifies the destination to which the return
//    value of this method will be sent. It sends the message to all subscribers of the /topic/public
//    destination.
//    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor
//    headerAccessor): This method handles messages sent by clients when they join the chat. It takes
//    a ChatMessage object representing the user joining and a SimpMessageHeaderAccessor object, which
//    allows access to the message header.
//    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender()): This line sets
//    the username of the sender (extracted from the chatMessage) as a session attribute. This is
//    typically done during user authentication or when a user joins the chat. The session attribute is
//    then used by other parts of the application, such as the WebSocketEventListener, to identify
//    users.
//    Finally, the method returns the received ChatMessage, which will be broadcasted to all clients
//    subscribed to /topic/public.

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
