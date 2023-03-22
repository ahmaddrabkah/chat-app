package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatMessageJpaRepository extends CrudRepository<ChatMessage, Long> {
    long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);
}
