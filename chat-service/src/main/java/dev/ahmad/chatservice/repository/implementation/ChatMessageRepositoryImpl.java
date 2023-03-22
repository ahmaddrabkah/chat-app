package dev.ahmad.chatservice.repository.implementation;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;
import dev.ahmad.chatservice.repository.ChatMessageJpaRepository;
import dev.ahmad.chatservice.repository.ChatMessageRepository;

import java.util.List;

public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageJpaRepository jpaRepository;

    public ChatMessageRepositoryImpl(ChatMessageJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status) {
        return jpaRepository.countBySenderIdAndReceiverIdAndStatus(senderId, receiverId, status);
    }

    @Override
    public List<ChatMessage> findByChatId(String chatId) {
        return jpaRepository.findByChatId(chatId);
    }
}
