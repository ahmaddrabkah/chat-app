package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ChatMessageJpaRepository extends CrudRepository<ChatMessage, Long> {

    Optional<ChatMessage> findById(Long id);

    List<ChatMessage> findByChatId(String chatId);

    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage c SET c.status = :status WHERE c.senderId = :senderId AND c.receiverId = :receiverId")
    void updateStatus(@Param("senderId") String senderId,
                      @Param("receiverId") String receiverId,
                      @Param("status") MessageStatus status);

    long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status);
}
