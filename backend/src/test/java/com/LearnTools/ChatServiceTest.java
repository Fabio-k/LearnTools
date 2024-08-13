package com.LearnTools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.LearnTools.LearnToolsApi.controller.dto.Request.ChatRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Chat;
import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;
import com.LearnTools.LearnToolsApi.model.repository.ChatRepository;
import com.LearnTools.LearnToolsApi.model.repository.MessagesRepository;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;
import com.LearnTools.LearnToolsApi.model.repository.ResumeRepository;
import com.LearnTools.LearnToolsApi.services.ChatService;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {
    @Mock
    private MessagesRepository messagesRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private PromptRepository promptRepository;

    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private AssistentRepository assistentRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void testGetOrCreateChat_ReturnChatResponse() {
        String username = "Test";
        Integer chatId = 1;
        Integer resumeId = 2;
        String testMessage = "test";
        String systemMessage = "should not appear";
        String systemRole = "system";
        String assistentRole = "Lucas";

        ChatRequest request = new ChatRequest();
        request.setResumeId(resumeId);

        MessagesEntity message1 = new MessagesEntity(LocalDateTime.now(), systemRole, systemMessage);
        MessagesEntity message2 = new MessagesEntity(LocalDateTime.now(), assistentRole, testMessage);

        List<MessagesEntity> messageList = new ArrayList<>();
        messageList.add(message1);
        messageList.add(message2);

        Resume resume = new Resume();
        resume.setId(resumeId);

        Chat chat = new Chat();
        chat.setResume(resume);
        chat.setMessages(messageList);
        chat.setId(chatId);
        chat.setAssistentName(assistentRole);

        List<Chat> listChats = new ArrayList<>();
        listChats.add(chat);

        when(messagesRepository.findAllByChatId(chatId)).thenReturn(messageList);
        when(chatRepository.findAllChatByUserUsername(username)).thenReturn(listChats);

        ChatResponse response = chatService.getOrCreateChat(username, request);

        assertEquals(assistentRole, response.getAssistentName());
        assertFalse(response.getIsNewChat());
        assertEquals(chatId, response.getChatId());
        assertEquals(testMessage, response.getMessages().first().getMessage());
    }

    @Test
    public void testGetOrCreateChat_throwBussinessExeception() {
        ChatRequest request = new ChatRequest();
        request.setResumeId(1);

        ChatRequest invalidRequest = new ChatRequest();

        assertThrows(BusinessException.class, () -> chatService.getOrCreateChat(null, request));
        assertThrows(BusinessException.class, () -> chatService.getOrCreateChat("Teste", invalidRequest));
    }

    @Test
    public void testGetOrCreateChat_WhenAssistentIdIsNull_ReturnIsNewChat() {
        String username = "Test";
        ChatRequest request = new ChatRequest();
        request.setResumeId(1);
        List<Chat> emptyList = new ArrayList<>();

        when(chatRepository.findAllChatByUserUsername(username)).thenReturn(emptyList);
        assertTrue(chatService.getOrCreateChat(username, request).getIsNewChat());
        assertNull(chatService.getOrCreateChat(username, request).getChatId());
        assertNull(chatService.getOrCreateChat(username, request).getAssistentName());
        assertNull(chatService.getOrCreateChat(username, request).getMessages());
    }

}
