package dev.ahmad.chatservice.repository.implementation;

import dev.ahmad.chatservice.model.ChatRoom;
import dev.ahmad.chatservice.repository.ChatRoomJpaRepository;
import dev.ahmad.chatservice.repository.ChatRoomRepository;

import java.util.Optional;

public class ChatRoomRepositoryImpl implements ChatRoomRepository {
    private final ChatRoomJpaRepository jpaRepository;

    public ChatRoomRepositoryImpl(ChatRoomJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        return jpaRepository.save(chatRoom);
    }

    @Override
    public Optional<ChatRoom> findBySenderIdAndReceiverId(String senderId, String receiverId) {
        return jpaRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }
}
