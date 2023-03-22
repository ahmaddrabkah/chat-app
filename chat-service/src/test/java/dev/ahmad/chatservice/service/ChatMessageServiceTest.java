package dev.ahmad.chatservice.service;

import dev.ahmad.chatservice.exception.MessageNotFoundException;
import dev.ahmad.chatservice.model.ChatMessage;
import dev.ahmad.chatservice.model.MessageStatus;
import dev.ahmad.chatservice.repository.ChatMessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

class ChatMessageServiceTest {

    private static ChatMessageRepository chatMessageRepository;
    private static ChatRoomService chatRoomService;
    private static ChatMessageService chatMessageService;

    @BeforeEach
    public void setup() {
        chatMessageRepository = Mockito.mock(ChatMessageRepository.class);
        chatRoomService = Mockito.mock(ChatRoomService.class);
        chatMessageService = new ChatMessageService(chatMessageRepository, chatRoomService);
    }

    @Test
    void givenChatMessage_whenSave_thenMessageShouldSavedAndReturnWithReceivedStatus() {
        ChatMessage chatMessage = getChatMessage();

        Mockito.when(chatMessageRepository.save(chatMessage)).thenReturn(chatMessage);

        ChatMessage actualMessage = chatMessageService.save(chatMessage);

        assertEqualMessages(chatMessage, actualMessage);
        Assertions.assertEquals(actualMessage.getStatus(), MessageStatus.RECEIVED);

        Mockito.verify(chatMessageRepository).save(chatMessage);
    }

    @Test
    void givenIdForExistingMessage_whenFindMessageById_thenMessageShouldSavedAndReturnWithDeliveredStatus() {
        ChatMessage chatMessage = getChatMessage();

        Mockito.when(chatMessageRepository.save(chatMessage)).thenReturn(chatMessage);
        Mockito.when(chatMessageRepository.findById(chatMessage.getId())).thenReturn(Optional.of(chatMessage));

        ChatMessage actualMessage = chatMessageService.findMessageById(chatMessage.getId());

        assertEqualMessages(chatMessage, actualMessage);
        Assertions.assertEquals(actualMessage.getStatus(), MessageStatus.DELIVERED);

        Mockito.verify(chatMessageRepository).save(chatMessage);
        Mockito.verify(chatMessageRepository).findById(chatMessage.getId());
    }

    @Test
    void givenIdForNoneExistingMessage_whenFindMessageById_thenShouldThrowMessageNotFoundException() {
        Mockito.when(chatMessageRepository.findById(1L)).thenReturn(Optional.empty());

        MessageNotFoundException exception = Assertions.assertThrows(MessageNotFoundException.class, () -> chatMessageService.findMessageById(1L));

        Assertions.assertEquals(exception.getMessage(), "message with 1 isn't found");

        Mockito.verify(chatMessageRepository, Mockito.never()).save(Mockito.any(ChatMessage.class));
        Mockito.verify(chatMessageRepository).findById(1L);
    }

    @Test
    void countMessageOfStatus() {
        Mockito.when(chatMessageRepository.countBySenderIdAndReceiverIdAndStatus("S1", "R1", MessageStatus.RECEIVED))
                .thenReturn(1L);

        long actualCount = chatMessageService.countMessageOfStatus("S1", "R1", MessageStatus.RECEIVED);

        Assertions.assertEquals(actualCount, 1);

        Mockito.verify(chatMessageRepository).countBySenderIdAndReceiverIdAndStatus("S1", "R1", MessageStatus.RECEIVED);
    }

    @Test
    void givenSenderAndReceiverIdsHaveNoMessages_whenFindChatMessages_thenShouldReturnEmptyList() {
        Mockito.when(chatRoomService.getChatId("S1", "R1"))
                .thenReturn(Optional.empty());

        List<ChatMessage> actualChatMessages = chatMessageService.findChatMessages("S1", "R1");

        Assertions.assertTrue(actualChatMessages.isEmpty());

        Mockito.verify(chatRoomService).getChatId("S1", "R1");
        Mockito.verify(chatMessageRepository, Mockito.never()).findByChatId(Mockito.any(String.class));
    }

    @Test
    void givenSenderAndReceiverIdsHaveMessages_whenFindChatMessages_thenShouldReturnListOfMessages() {
        List<ChatMessage> chatMessages = List.of(getChatMessage());

        Mockito.when(chatRoomService.getChatId("S1", "R1")).thenReturn(Optional.of("C1"));
        Mockito.when(chatMessageRepository.findByChatId("C1")).thenReturn(chatMessages);

        List<ChatMessage> actualChatMessages = chatMessageService.findChatMessages("S1", "R1");

        Assertions.assertFalse(actualChatMessages.isEmpty());
        assertEqualMessages(chatMessages.get(0), actualChatMessages.get(0));

        Mockito.verify(chatRoomService).getChatId("S1", "R1");
        Mockito.verify(chatMessageRepository).findByChatId("C1");
        Mockito.verify(chatMessageRepository).updateStatus("S1", "R1", MessageStatus.DELIVERED);
    }

    private ChatMessage getChatMessage() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(1L);
        chatMessage.setChatId("Chat-1");
        chatMessage.setSenderId("Sender-1");
        chatMessage.setReceiverId("Receiver-1");
        chatMessage.setSenderName("Ahmad");
        chatMessage.setReceiverName("Mohammad");
        chatMessage.setContent("Hi Mohammad, how are you ?");
        return chatMessage;
    }

    private void assertEqualMessages(ChatMessage chatMessage, ChatMessage actualMessage) {
        Assertions.assertEquals(chatMessage.getId(), actualMessage.getId());
        Assertions.assertEquals(chatMessage.getChatId(), actualMessage.getChatId());
        Assertions.assertEquals(chatMessage.getSenderId(), actualMessage.getSenderId());
        Assertions.assertEquals(chatMessage.getReceiverId(), actualMessage.getReceiverId());
        Assertions.assertEquals(chatMessage.getSenderName(), actualMessage.getSenderName());
        Assertions.assertEquals(chatMessage.getReceiverName(), actualMessage.getReceiverName());
        Assertions.assertEquals(chatMessage.getContent(), actualMessage.getContent());
    }
}