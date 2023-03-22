package dev.ahmad.chatservice.service;

import dev.ahmad.chatservice.model.ChatRoom;
import dev.ahmad.chatservice.repository.ChatRoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class ChatRoomServiceTest {

    private ChatRoomRepository chatRoomRepository;
    private ChatRoomService chatRoomService;

    @BeforeEach
    public void setup() {
        chatRoomRepository = Mockito.mock(ChatRoomRepository.class);
        chatRoomService = new ChatRoomService(chatRoomRepository);
    }

    @Test
    void givenSenderAndReceiverIdsHaveNoChatRoom_whenGetChatId_thenShouldReturnOptionalOfEmpty() {
        Mockito.when(chatRoomRepository.findBySenderIdAndReceiverId("S1", "R1")).thenReturn(Optional.empty());

        Optional<String> actualChatId = chatRoomService.getChatId("S1", "R1");

        Assertions.assertTrue(actualChatId.isEmpty());

        Mockito.verify(chatRoomRepository).findBySenderIdAndReceiverId("S1", "R1");
    }

    @Test
    void givenSenderAndReceiverIds_whenCreate_thenShouldCreateNewChatRoomsForSenderAndReceiver() {
        ChatRoom chatRoom = new ChatRoom();

        Mockito.when(chatRoomRepository.save(Mockito.any(ChatRoom.class))).thenReturn(chatRoom);

        String actualChatId = chatRoomService.create("S1", "R1");

        Assertions.assertFalse(actualChatId.isEmpty());

        Mockito.verify(chatRoomRepository, Mockito.atMost(2)).save(Mockito.any(ChatRoom.class));
    }
}