package dev.ahmad.chatservice.configuration;

import dev.ahmad.chatservice.repository.ChatMessageJpaRepository;
import dev.ahmad.chatservice.repository.ChatMessageRepository;
import dev.ahmad.chatservice.repository.ChatRoomJpaRepository;
import dev.ahmad.chatservice.repository.ChatRoomRepository;
import dev.ahmad.chatservice.repository.implementation.ChatMessageRepositoryImpl;
import dev.ahmad.chatservice.repository.implementation.ChatRoomRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public ChatMessageRepository chatMessageRepository(ChatMessageJpaRepository jpaRepository) {
        return new ChatMessageRepositoryImpl(jpaRepository);
    }

    @Bean
    public ChatRoomRepository chatRoomRepository(ChatRoomJpaRepository jpaRepository) {
        return new ChatRoomRepositoryImpl(jpaRepository);
    }
}
