package com.LearnTools.LearnToolsApi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithMockUser;

import com.LearnTools.LearnToolsApi.controller.dto.Request.FlashcardRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.FlashcardResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Flashcard;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.FlashCardTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.FlashcardRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.services.FlashcardService;
import com.LearnTools.LearnToolsApi.services.TagService;
import com.LearnTools.LearnToolsApi.services.UserService;

@WithMockUser(username = "testuser", roles = { "USER" })
public class FlashcardServiceTest {

    @Mock
    private FlashcardRepository flashcardRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagService tagService;

    @Mock
    private FlashCardTagRepository flashCardTagRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FlashcardService flashcardService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFlashcards() {
        // Arrange
        String username = "testuser";
        User user = new User(username, username, "teste", "USER");
        List<Flashcard> flashcards = new ArrayList<>();

        Flashcard flashcard1 = new Flashcard("Question 1", "Answer 1");
        flashcard1.setUser(user);

        Flashcard flashcard2 = new Flashcard("Question 2", "Answer 2");
        flashcard2.setUser(user);

        flashcards.add(flashcard1);
        flashcards.add(flashcard2);

        when(flashcardRepository.findAllByUserUsername(username)).thenReturn(flashcards);

        // Act
        List<FlashcardResponse> flashcardResponses = flashcardService.getFlashcards(username);

        // Assert
        assertEquals(2, flashcardResponses.size());
        assertEquals("Question 1", flashcardResponses.get(0).getQuestion());
        assertEquals("Answer 1", flashcardResponses.get(0).getAnswer());
        assertEquals("Question 2", flashcardResponses.get(1).getQuestion());
        assertEquals("Answer 2", flashcardResponses.get(1).getAnswer());
    }

    @Test
    public void testPostFlashcard() {
        // Arrange
        String username = "testuser";
        FlashcardRequest flashcardRequest = new FlashcardRequest("Question", "Answer", List.of("Tag1", "Tag2"));
        User user = new User();
        user.setUsername(username);
        Tag tag1 = new Tag("tag1", "red");
        Tag tag2 = new Tag("tag2", "red");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag2);
        tags.add(tag1);
        when(userService.getUser(username)).thenReturn(user);
        when(flashcardRepository.save(any(Flashcard.class))).thenReturn(new Flashcard());
        when(tagService.getUserTags(username)).thenReturn(tags);
        // Act
        flashcardService.postFlashcard(flashcardRequest, username);

        // Assert
        // No exceptions are thrown
    }

    @Test
    public void testPostFlashcard_InvalidTag() {
        // Arrange
        String username = "testuser";
        FlashcardRequest flashcardRequest = new FlashcardRequest("Question", "Answer", List.of("Tag1", "Tag2"));
        User user = new User();
        user.setUsername(username);
        when(userService.getUser(username)).thenReturn(user);
        when(tagService.getUserTags(username)).thenReturn(new ArrayList<>());

        // Act & Assert
        assertThrows(BusinessException.class, () -> flashcardService.postFlashcard(flashcardRequest, username));
    }
}
