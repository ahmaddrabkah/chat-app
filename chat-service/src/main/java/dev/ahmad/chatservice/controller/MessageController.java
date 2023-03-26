package dev.ahmad.chatservice.controller;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.ChatNotification;
import dev.ahmad.chatservice.model.MessageStatus;
import dev.ahmad.chatservice.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class MessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    public MessageController(SimpMessagingTemplate simpMessagingTemplate, ChatMessageService chatMessageService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {
        ChatNotification chatNotification = chatMessageService.processMessage(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverId(), "/queue/messages", chatNotification);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/{senderId}/{receiverId}/count")
    public @ResponseBody Long countReceivedMessages(@PathVariable String senderId, @PathVariable String receiverId) {
        return chatMessageService.countMessageOfStatus(senderId, receiverId, MessageStatus.RECEIVED);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/{senderId}/{receiverId}")
    public @ResponseBody List<ChatMessage> findChatMessages(@PathVariable String senderId, @PathVariable String receiverId) {
        return chatMessageService.findChatMessages(senderId, receiverId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/{id}")
    public @ResponseBody ChatMessage findMessage(@PathVariable Long id) {
        return chatMessageService.findMessageById(id);
    }

}
