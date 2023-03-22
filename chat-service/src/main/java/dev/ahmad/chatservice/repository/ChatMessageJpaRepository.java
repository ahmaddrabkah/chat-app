package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageJpaRepository extends CrudRepository<ChatMessage, Long> {

    Optional<ChatMessage> findById(Long id);

    List<ChatMessage> findByChatId(String chatId);

    @Query(value = "UPDATE ChatMessage chatMessage SET chatMessage.status = :status WHERE chatMessage.senderId = :senderId AND chatMessage.receiverId = :receiverId")
    void updateStatus(String senderId, String receiverId, MessageStatus status);

    long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status);
}
