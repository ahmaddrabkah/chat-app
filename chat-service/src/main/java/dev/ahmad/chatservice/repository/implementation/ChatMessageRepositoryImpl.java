package dev.ahmad.chatservice.repository.implementation;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;
import dev.ahmad.chatservice.repository.ChatMessageJpaRepository;
import dev.ahmad.chatservice.repository.ChatMessageRepository;

import java.util.List;
import java.util.Optional;

public class ChatMessageRepositoryImpl implements ChatMessageRepository {

    private final ChatMessageJpaRepository jpaRepository;

    public ChatMessageRepositoryImpl(ChatMessageJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return jpaRepository.save(chatMessage);
    }

    @Override
    public Optional<ChatMessage> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<ChatMessage> findByChatId(String chatId) {
        return jpaRepository.findByChatId(chatId);
    }

    @Override
    public void updateStatus(String senderId, String receiverId, MessageStatus status) {
        jpaRepository.updateStatus(senderId, receiverId, status);
    }

    @Override
    public long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status) {
        return jpaRepository.countBySenderIdAndReceiverIdAndStatus(senderId, receiverId, status);
    }
}
