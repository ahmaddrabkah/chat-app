package dev.ahmad.chatservice.repository;

import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;

import java.util.List;

public interface ChatMessageRepository {
    long countBySenderIdAndReceiverIdAndStatus(String senderId, String receiverId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);
}
