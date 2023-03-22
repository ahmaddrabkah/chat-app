package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository {

    ChatRoom save(ChatRoom chatRoom);
    Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId);
}
