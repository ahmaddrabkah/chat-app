package dev.ahmad.chatservice.service;

import dev.ahmad.chatservice.repository.ChatMessageRepository;
import dev.ahmad.chatservice.repository.ChatRoomRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public ChatMessageService chatMessageService(ChatMessageRepository chatMessageRepository, ChatRoomService chatRoomService) {
        return new ChatMessageService(chatMessageRepository, chatRoomService);
    }

    @Bean
    public ChatRoomService chatRoomService(ChatRoomRepository chatRoomRepository) {
        return new ChatRoomService(chatRoomRepository);
    }
}
