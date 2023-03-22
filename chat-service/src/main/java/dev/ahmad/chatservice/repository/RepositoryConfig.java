package dev.ahmad.chatservice.repository;

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
