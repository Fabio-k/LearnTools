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
import com.LearnTools.LearnToolsApi.controller.dto.Request.AiResumeRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Request.MessageRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.AiResumeResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ChatResponse;
import com.LearnTools.LearnToolsApi.controller.dto.Response.MessagesResponse;
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

    public AiResumeResponse handleAiChatReponse(UserDetails userDetails, MessageRequest messageDTO, Integer id) {
        List<Chat> userChats = getUserChats(userDetails);
        Optional<Chat> matchChat = userChats.stream().filter(c -> c.getId() == id).findFirst();
        if (matchChat.isEmpty())
            throw new BusinessException("chat not found");

        saveMessage(matchChat.get(), messageDTO.getMessage(), ROLE_USER);

        List<MessagesEntity> messagesEntityList = messagesRepository.findAllByChatId(matchChat.get().getId());
        messagesEntityList.sort(Comparator.comparing(MessagesEntity::getTimestamp));

        List<MessagesResponse> messagesList = messagesEntityList.stream().map(MessagesResponse::fromEntity)
                .collect(Collectors.toList());
        AiResumeResponse aiResumeResponse = createAiResumeResponse(messageDTO, messagesList, matchChat.get());
        saveMessage(matchChat.get(), aiResumeResponse);

        return aiResumeResponse;

    }

    public AiResumeResponse handleNewChat(UserDetails userDetails, MessageRequest messageDTO) {
        List<Resume> userResumes = getUserResumes(userDetails);
        Optional<Resume> matchResume = userResumes.stream().filter(r -> r.getId() == messageDTO.getResumeID())
                .findFirst();
        if (matchResume.isEmpty())
            throw new BusinessException("resume not found");

        Prompt matchBase = promptRepository.findByName(RESUME_PROMPT);
        String matchBaseContent = matchBase.getPrompt();
        Optional<Assistent> matchPrompt = assistentRepository.findById(messageDTO.getAssistentID());

        if (matchPrompt.isEmpty())
            throw new BusinessException("assistente não encontrado");

        Assistent assistentPrompt = matchPrompt.get();

        matchBaseContent = matchBaseContent.replace(NAME_SUBSTITUTION, assistentPrompt.getName());
        matchBaseContent = matchBaseContent.replace(PROMPT_SUBSTITUTION, assistentPrompt.getPrompt());
        matchBaseContent = matchBaseContent.replace(RESUME_SUBSTITUTION, matchResume.get().getDescription());

        Chat chat = new Chat();
        chat.setTitle(matchResume.get().getTitle());

        User user = userService.getUser(userDetails.getUsername());
        Optional<Resume> resumeOptional = resumeRepository.findById(messageDTO.getResumeID());
        Resume resume = resumeOptional.get();
        chat.setUser(user);
        chat.setResume(resume);
        chatRepository.save(chat);

        MessagesEntity systemMessage = saveMessage(chat, matchBaseContent, ROLE_SYSTEM);
        MessagesResponse messages = new MessagesResponse();
        messages.setRole(systemMessage.getOrigin());
        messages.setContent(systemMessage.getMessage());

        List<MessagesResponse> listMessages = new ArrayList<>();
        listMessages.add(messages);
        AiResumeResponse aiResponse = createAiResumeResponse(messageDTO, listMessages, chat);
        saveMessage(chat, aiResponse);

        return aiResponse;
    }

    private AiResumeResponse createAiResumeResponse(MessageRequest messageDTO, List<MessagesResponse> listMessages,
            Chat chat) {
        AiResumeRequest aiResumeRequest = new AiResumeRequest();
        aiResumeRequest.setModel(messageDTO.getModel());
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

    private List<Chat> getUserChats(UserDetails userDetails) {
        List<Chat> userChats = chatRepository.findAllChatByUserUsername(userDetails.getUsername());
        return userChats;
    }

    private List<Resume> getUserResumes(UserDetails userDetails) {
        List<Resume> userResumes = resumeRepository.findAllByUserUsername(userDetails.getUsername());
        return userResumes;
    }

    public ChatResponse getAllChats(String username) {
        ChatResponse response = new ChatResponse();
        Comparator<MessageDto> Comparator = (messages1, messages2) -> messages1.getTime()
                .compareTo(messages2.getTime());

        List<Chat> chatList = chatRepository.findAllChatByUserUsername(username);

        for (Chat chat : chatList) {
            Integer chatId = chat.getId();
            Integer resumeId = chat.getResume().getId();
            List<MessagesEntity> messages = messagesRepository.findAllByChatId(chatId).stream()
                    .filter(message -> !message.getOrigin().equals(ROLE_SYSTEM)).collect(Collectors.toList());
            List<MessageDto> messageDto = messages.stream().map(MessageDto::fromEntity).collect(Collectors.toList());
            TreeSet<MessageDto> messageSet = new TreeSet<>(Comparator);
            messageSet.addAll(messageDto);
            response.getChats().put(resumeId, messageSet);
        }

        return response;
    }

}
