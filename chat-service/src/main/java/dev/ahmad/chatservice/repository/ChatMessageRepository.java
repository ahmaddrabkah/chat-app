package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository {

    ChatMessage save(ChatMessage chatMessage);

    Optional<ChatMessage> findById(Long id);

    List<ChatMessage> findByChatId(String chatId);

    void updateStatus(String senderId, String receiverId, MessageStatus status);

    long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status);
}
