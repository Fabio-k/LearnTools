package com.LearnTools.LearnToolsApi.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.client.AiClient;
import com.LearnTools.LearnToolsApi.controller.dto.MessageDto;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiResumeRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiResumeResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Request.ChatRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.MessageRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatStatusResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.SimpleMessage;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Assistent;
import com.LearnTools.LearnToolsApi.model.entidades.Chat;
import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.AssistentRepository;
import com.LearnTools.LearnToolsApi.model.repository.ChatRepository;
import com.LearnTools.LearnToolsApi.model.repository.MessagesRepository;
import com.LearnTools.LearnToolsApi.model.repository.PromptRepository;
import com.LearnTools.LearnToolsApi.model.repository.ResumeRepository;

@Service
public class ChatService {
    private final MessagesRepository messagesRepository;
    private final ChatRepository chatRepository;
    private final PromptRepository promptRepository;
    private final ResumeRepository resumeRepository;
    private final AssistentRepository assistentRepository;

    private final UserService userService;

    private final AiClient aiClient;

    private final String RESUME_PROMPT = "prompt resume base";
    private final String NAME_SUBSTITUTION = "$NAME";
    private final String PROMPT_SUBSTITUTION = "$ASSISTENT_PROMPT";
    private final String RESUME_SUBSTITUTION = "$Resume";
    private final String ROLE_USER = "user";
    private final String ROLE_SYSTEM = "system";

    public ChatService(MessagesRepository messagesRepository, ChatRepository chatRepository,
            PromptRepository promptRepository, ResumeRepository resumeRepository,
            AssistentRepository assistentRepository, UserService userService, AiClient aiClient) {
        this.messagesRepository = messagesRepository;
        this.chatRepository = chatRepository;
        this.promptRepository = promptRepository;
        this.resumeRepository = resumeRepository;
        this.assistentRepository = assistentRepository;
        this.userService = userService;
        this.aiClient = aiClient;
    }

    public ChatStatusResponse getStatus(String username, Integer chatId) {
        ChatStatusResponse response = new ChatStatusResponse();
        Optional<Chat> oprionalChat = findUserChat(username, chatId);
        response.setIsCreated(oprionalChat.isPresent());
        return response;
    }

    public ChatResponse getOrCreateChat(String username, ChatRequest request) {
        if (request.getResumeId() == null) {
            Chat chat = handleNewChat(username, request);
            return formatChatToChatResponse(chat, true);
        }

        Optional<Chat> optionalChat = chatRepository.findAllChatByUserUsername(username).stream()
                .filter(c -> c.getResume().getId() == request.getResumeId()).findFirst();

        if (optionalChat.isEmpty()) {
            Chat chat = handleNewChat(username, request);
            return formatChatToChatResponse(chat, true);
        }
        return formatChatToChatResponse(optionalChat.get(), false);
    }

    public AiResumeResponse handleAiChatReponse(UserDetails userDetails, MessageRequest request, Integer id) {
        Optional<Chat> matchChat = findUserChat(userDetails.getUsername(), id);
        if (matchChat.isEmpty())
            throw new BusinessException("chat not found");

        saveMessage(matchChat.get(), request.getMessage(), ROLE_USER);

        List<MessagesEntity> messagesEntityList = messagesRepository.findAllByChatId(matchChat.get().getId());
        messagesEntityList.sort(Comparator.comparing(MessagesEntity::getTimestamp));

        List<SimpleMessage> messagesList = messagesEntityList.stream().map(SimpleMessage::fromEntity)
                .collect(Collectors.toList());

        AiResumeResponse aiResumeResponse = createAiResumeResponse(request, messagesList, matchChat.get());
        saveMessage(matchChat.get(), aiResumeResponse);

        return aiResumeResponse;

    }

    public Chat handleNewChat(String username, ChatRequest request) {
        List<Resume> userResumes = getUserResumes(username);
        Optional<Resume> matchResume = userResumes.stream().filter(r -> r.getId() == request.getResumeId())
                .findFirst();
        if (matchResume.isEmpty())
            throw new BusinessException("resume not found");

        Optional<Assistent> optionalAssistent = assistentRepository.findById(request.getAssistentId());

        if (optionalAssistent.isEmpty())
            throw new BusinessException("assistente não encontrado");

        Assistent assistent = optionalAssistent.get();

        Resume resume = matchResume.get();
        User user = userService.getUser(username);

        Chat chat = new Chat();
        chat.setUser(user);
        chat.setResume(resume);
        chat.setAssistentName(assistent.getName());
        chatRepository.save(chat);

        SimpleMessage systemMessage = createSystemMessage(chat, resume.getDescription(), assistent);

        List<SimpleMessage> listMessages = new ArrayList<>();
        listMessages.add(systemMessage);

        AiResumeResponse aiResponse = createAiResumeResponse(request, listMessages, chat);
        saveMessage(chat, aiResponse);

        return chat;
    }

    public void deleteChat(String username, Integer chatId) {
        Optional<Chat> chat = findUserChat(username, chatId);

        if (chat.isEmpty())
            throw new BusinessException("chat not found");

        chatRepository.deleteById(chatId);
    }

    private ChatResponse formatChatToChatResponse(Chat chat, Boolean isNewChat) {
        ChatResponse chatResponse = new ChatResponse();
        Comparator<MessageDto> Comparator = (messages1, messages2) -> messages1.getTime()
                .compareTo(messages2.getTime());

        List<MessagesEntity> messages = messagesRepository.findAllByChatId(chat.getId()).stream()
                .filter(message -> !message.getOrigin().equals(ROLE_SYSTEM)).collect(Collectors.toList());
        List<MessageDto> messageDto = messages.stream().map(MessageDto::fromEntity).collect(Collectors.toList());
        TreeSet<MessageDto> messageSet = new TreeSet<>(Comparator);
        messageSet.addAll(messageDto);

        chatResponse.setChatId(chat.getId());
        chatResponse.setAssistentName(chat.getAssistentName());
        chatResponse.setMessages(messageSet);
        chatResponse.setIsNewChat(isNewChat);
        chatResponse.setMessages(messageSet);

        return chatResponse;
    }

    private AiResumeResponse createAiResumeResponse(ChatRequest request, List<SimpleMessage> listMessages,
            Chat chat) {
        AiResumeRequest aiResumeRequest = new AiResumeRequest();
        aiResumeRequest.setModel(request.getModel());
        aiResumeRequest.setMessages(listMessages);
        aiResumeRequest.setStream(false);

        AiResumeResponse aiResumeResponse = aiClient.getResumeResponse(aiResumeRequest);
        aiResumeResponse.setChatId(chat.getId());

        return aiResumeResponse;
    }

    private void saveMessage(Chat chat, AiResumeResponse aiResumeResponse) {
        String content = aiResumeResponse.getMessage().getContent();
        String role = aiResumeResponse.getMessage().getRole();
        MessagesEntity newMessage = new MessagesEntity(chat, content,
                role, LocalDateTime.now());

        messagesRepository.save(newMessage);
    }

    private MessagesEntity saveMessage(Chat chat, String content, String role) {
        MessagesEntity newMessage = new MessagesEntity(chat, content, role, LocalDateTime.now());
        messagesRepository.save(newMessage);
        return newMessage;
    }

    private Optional<Chat> findUserChat(String username, Integer chatId) {
        List<Chat> userChats = chatRepository.findAllChatByUserUsername(username);
        Optional<Chat> optionalChat = userChats.stream()
                .filter(c -> c.getResume().getId() == chatId).findFirst();
        return optionalChat;
    }

    private List<Resume> getUserResumes(String username) {
        List<Resume> userResumes = resumeRepository.findAllByUserUsername(username);
        return userResumes;
    }

    private SimpleMessage createSystemMessage(Chat chat, String resumeContent, Assistent assistent) {
        Prompt matchBase = promptRepository.findByName(RESUME_PROMPT);
        String BasePrompt = matchBase.getPrompt();

        BasePrompt = BasePrompt.replace(NAME_SUBSTITUTION, assistent.getName());
        BasePrompt = BasePrompt.replace(PROMPT_SUBSTITUTION, assistent.getPrompt());
        BasePrompt = BasePrompt.replace(RESUME_SUBSTITUTION, resumeContent);

        MessagesEntity SystemMessage = saveMessage(chat, BasePrompt, ROLE_SYSTEM);
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setContent(SystemMessage.getMessage());
        simpleMessage.setRole(SystemMessage.getOrigin());

        return simpleMessage;
    }

}
