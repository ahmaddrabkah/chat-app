package dev.ahmad.chatservice.service;

import dev.ahmad.chatservice.exception.MessageNotFoundException;
import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.ChatNotification;
import dev.ahmad.chatservice.model.MessageStatus;
import dev.ahmad.chatservice.repository.ChatMessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatRoomService chatRoomService) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomService = chatRoomService;
    }

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        return chatMessageRepository.save(chatMessage);
    }

    public ChatMessage findMessageById(Long id) {
        return chatMessageRepository.findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                })
                .orElseThrow(() -> new MessageNotFoundException(String.format("message with %s isn't found", id)));
    }

    public long countMessageOfStatus(String senderId, String receiverId, MessageStatus status) {
        return chatMessageRepository.countBySenderIdAndReceiverIdAndStatus(senderId, receiverId, status);
    }

    public List<ChatMessage> findChatMessages(String senderId, String receiverId) {
        Optional<String> chatId = chatRoomService.getChatId(senderId, receiverId);
        List<ChatMessage> chatMessages = chatId.map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());
        if (!chatMessages.isEmpty()) {
            updateStatus(senderId, receiverId, MessageStatus.DELIVERED);
        }
        return chatMessages;
    }

    public void updateStatus(String senderId, String receiverId, MessageStatus status) {
        chatMessageRepository.updateStatus(senderId, receiverId, status);
    }

    public ChatNotification processMessage(ChatMessage chatMessage) {
        String chatId = chatRoomService.getChatId(chatMessage.getSenderId(), chatMessage.getReceiverId())
                .orElseGet(() -> chatRoomService.create(chatMessage.getSenderId(), chatMessage.getReceiverId()));
        chatMessage.setChatId(chatId);

        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);

        return new ChatNotification(savedChatMessage.getId(), savedChatMessage.getSenderId(), savedChatMessage.getSenderName());
    }

}