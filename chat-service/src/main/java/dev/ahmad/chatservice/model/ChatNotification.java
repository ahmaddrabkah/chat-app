package dev.ahmad.chatservice.model;

public class ChatNotification {
    private Long messageId;
    private String senderId;
    private String senderName;

    public ChatNotification() {
    }

    public ChatNotification(Long id, String senderId, String senderName) {
        this.messageId = id;
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}