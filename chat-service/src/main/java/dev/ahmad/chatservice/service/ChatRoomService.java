package dev.ahmad.chatservice.service;

import dev.ahmad.chatservice.model.ChatRoom;
import dev.ahmad.chatservice.repository.ChatRoomRepository;

import java.util.Optional;
import java.util.UUID;

public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public Optional<String> getChatId(String senderId, String receiverId) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(ChatRoom::getChatId);
    }

    public String create(String senderId, String receiverId) {
        String chatId = UUID.randomUUID().toString();

        ChatRoom senderChatRoom = new ChatRoom();
        senderChatRoom.setChatId(chatId);
        senderChatRoom.setSenderId(senderId);
        senderChatRoom.setReceiverId(receiverId);

        ChatRoom receiverChatRoom = new ChatRoom();
        receiverChatRoom.setChatId(chatId);
        receiverChatRoom.setSenderId(receiverId);
        receiverChatRoom.setReceiverId(senderId);

        chatRoomRepository.save(senderChatRoom);
        chatRoomRepository.save(receiverChatRoom);

        return chatId;
    }
}
