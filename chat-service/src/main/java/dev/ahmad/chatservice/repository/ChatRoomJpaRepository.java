package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ChatRoomJpaRepository extends CrudRepository<ChatRoom, Long> {
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
