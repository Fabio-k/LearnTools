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

import com.LearnTools.LearnToolsApi.controller.dto.MessageDto;
import com.LearnTools.LearnToolsApi.controller.dto.Client.AiGenerateResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Request.ChatRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.MessageRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiMessageResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.SimpleMessage;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Chat;
import com.LearnTools.LearnToolsApi.model.entidades.MessagesEntity;
import com.LearnTools.LearnToolsApi.model.entidades.Prompt;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.model.entidades.User;
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

    private final UserService userService;
    private final AiService aiService;

    private final String RESUME_PROMPT = "prompt resume base";
    private final String NAME_SUBSTITUTION = "$NAME";
    private final String PROMPT_SUBSTITUTION = "$ASSISTENT_PROMPT";
    private final String RESUME_SUBSTITUTION = "$Resume";
    private final String ROLE_USER = "user";
    private final String ROLE_SYSTEM = "system";

    public ChatService(MessagesRepository messagesRepository, ChatRepository chatRepository,
            PromptRepository promptRepository, ResumeRepository resumeRepository, UserService userService,
            AiService aiService) {
        this.messagesRepository = messagesRepository;
        this.chatRepository = chatRepository;
        this.promptRepository = promptRepository;
        this.resumeRepository = resumeRepository;
        this.userService = userService;
        this.aiService = aiService;
    }

    public ChatResponse getOrCreateChat(String username, ChatRequest request) {
        if (request.getResumeId() == null)
            throw new BusinessException("resumeId cant be null");
        if (username == null)
            throw new BusinessException("username cant be null");

        Optional<Chat> optionalChat = chatRepository.findAllChatByUserUsername(username).stream()
                .filter(c -> c.getResume().getId() == request.getResumeId()).findFirst();

        if (optionalChat.isEmpty()) {
            if (request.getAssistentId() == null || request.getModel() == null) {
                ChatResponse response = new ChatResponse();
                response.setIsNewChat(true);
                return response;
            }
            Chat chat = handleNewChat(username, request);
            return formatChatToChatResponse(chat, true);
        }
        return formatChatToChatResponse(optionalChat.get(), false);
    }

    public AiMessageResponse handleAiChatReponse(UserDetails userDetails, MessageRequest request, Integer id) {
        Optional<Chat> matchChat = findUserChatByChatId(userDetails.getUsername(), id);
        if (matchChat.isEmpty())
            throw new BusinessException("chat not found");
        Chat chat = matchChat.get();
        saveMessage(chat, request.getMessage(), ROLE_USER);

        List<MessagesEntity> messagesEntityList = messagesRepository.findAllByChatId(matchChat.get().getId());
        messagesEntityList.sort(Comparator.comparing(MessagesEntity::getTimestamp));

        List<SimpleMessage> messagesList = messagesEntityList.stream().map(SimpleMessage::fromEntity)
                .collect(Collectors.toList());

        AiGenerateResponse aiGenerateResponse = aiService.getAiResponse(request.getModel(), messagesList);
        saveMessage(matchChat.get(), aiGenerateResponse);

        MessageDto message = new MessageDto();
        message.setMessage(aiGenerateResponse.getMessage().getContent());
        message.setRole(aiGenerateResponse.getMessage().getRole());

        AiMessageResponse aiMessage = new AiMessageResponse();
        aiMessage.setChatId(chat.getId());
        aiMessage.setMessage(message);

        return aiMessage;

    }

    public Chat handleNewChat(String username, ChatRequest request) {
        List<Resume> userResumes = getUserResumes(username);
        Optional<Resume> matchResume = userResumes.stream().filter(r -> r.getId() == request.getResumeId())
                .findFirst();
        if (matchResume.isEmpty())
            throw new BusinessException("resume not found");

        Optional<Prompt> optionalAssistent = promptRepository.findById(request.getAssistentId());

        if (optionalAssistent.isEmpty())
            throw new BusinessException("assistente não encontrado");

        Prompt assistent = optionalAssistent.get();

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

        AiGenerateResponse aiResponse = aiService.getAiResponse(request.getModel(), listMessages);
        saveMessage(chat, aiResponse);

        return chat;
    }

    public void deleteChat(String username, Integer chatId) {
        Optional<Chat> chat = findUserChatByChatId(username, chatId);

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

    private void saveMessage(Chat chat, AiGenerateResponse AiGenerateResponse) {
        String content = AiGenerateResponse.getMessage().getContent();
        String role = AiGenerateResponse.getMessage().getRole();
        MessagesEntity newMessage = new MessagesEntity(chat, content,
                role, LocalDateTime.now());

        messagesRepository.save(newMessage);
    }

    private MessagesEntity saveMessage(Chat chat, String content, String role) {
        MessagesEntity newMessage = new MessagesEntity(chat, content, role, LocalDateTime.now());
        messagesRepository.save(newMessage);
        return newMessage;
    }

    private Optional<Chat> findUserChatByChatId(String username, Integer chatId) {
        List<Chat> userChats = chatRepository.findAllChatByUserUsername(username);
        Optional<Chat> optionalChat = userChats.stream()
                .filter(c -> c.getId() == chatId).findFirst();
        return optionalChat;
    }

    private List<Resume> getUserResumes(String username) {
        List<Resume> userResumes = resumeRepository.findAllByUserUsername(username);
        return userResumes;
    }

    private SimpleMessage createSystemMessage(Chat chat, String resumeContent, Prompt prompt) {
        Prompt matchBase = promptRepository.findByName(RESUME_PROMPT);
        String BasePrompt = matchBase.getPrompt();

        BasePrompt = BasePrompt.replace(NAME_SUBSTITUTION, prompt.getName());
        BasePrompt = BasePrompt.replace(PROMPT_SUBSTITUTION, prompt.getPrompt());
        BasePrompt = BasePrompt.replace(RESUME_SUBSTITUTION, resumeContent);

        MessagesEntity SystemMessage = saveMessage(chat, BasePrompt, ROLE_SYSTEM);

        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setContent(SystemMessage.getMessage());
        simpleMessage.setRole(SystemMessage.getOrigin());

        return simpleMessage;
    }

}
