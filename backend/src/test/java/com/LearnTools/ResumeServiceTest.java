package com.LearnTools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.LearnTools.LearnToolsApi.controller.dto.Request.ResumesRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ResumesResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.ResumeRepository;
import com.LearnTools.LearnToolsApi.model.repository.ResumeTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;
import com.LearnTools.LearnToolsApi.services.ResumesService;
import com.LearnTools.LearnToolsApi.services.UserService;

@ExtendWith(MockitoExtension.class)
public class ResumeServiceTest {
    @Mock
    private ResumeRepository resumeRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ResumeTagRepository resumeTagRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ResumesService resumesService;

    @Mock
    private UserService userService;

    @Test
    public void testCreateResume_postSucesfully() {
        ResumesRequest request = new ResumesRequest("Test", "Test", null);
        String username = "Test";
        String resumeTitle = "ResumeTitle";
        String resumeDescription = "resume description";
        Resume resume = new Resume(resumeTitle, resumeDescription);

        User user = new User();
        user.setUsername(username);

        when(userService.getUser(username)).thenReturn(user);
        when(resumeRepository.save(any(Resume.class))).thenReturn(resume);

        ResumesResponse response = resumesService.createResume(request, username);

        assertEquals(resumeTitle, response.getTitle());
        assertEquals(resumeDescription, response.getDescription());
    }

    @Test
    public void testDeleteResume_deleteSucessfully() {
        String username = "Test";
        Integer resumeId = 1;
        String resumeTitle = "ResumeTitle";
        String resumeDescription = "resume description";

        Resume resume = new Resume(resumeTitle, resumeDescription);
        resume.setId(resumeId);

        List<Resume> listResume = new ArrayList<>();
        listResume.add(resume);

        when(resumeRepository.findAllByUserUsername(username)).thenReturn(listResume);
        resumesService.deleteResume(username, resumeId);
        verify(resumeRepository, times(1)).delete(any(Resume.class));
    }

    @Test
    public void testDeleteResume_throwBussinessException() {
        String username = "Test";
        Integer resumeId = 1;
        String resumeTitle = "ResumeTitle";
        String resumeDescription = "resume description";

        Resume resume = new Resume(resumeTitle, resumeDescription);
        resume.setId(resumeId);

        List<Resume> emptyResume = new ArrayList<>();

        when(resumeRepository.findAllByUserUsername(username)).thenReturn(emptyResume);
        assertThrows(BusinessException.class, () -> resumesService.deleteResume(username, resumeId));
        assertThrows(BusinessException.class, () -> resumesService.deleteResume(username, null));
        assertThrows(BusinessException.class, () -> resumesService.deleteResume(null, resumeId));
    }

    @Test
    public void testPatchResume_success() {
        String username = "Test";
        Integer resumeId = 1;
        String title = "title";
        String description = "description";
        String titleAlteration = "title alteration";
        String descritionAlteration = "description alteration";

        ResumesRequest request = new ResumesRequest(titleAlteration, descritionAlteration, null);
        Resume resume = new Resume(title, description);
        resume.setId(resumeId);

        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(resume);

        when(resumeRepository.findAllByUserUsername(username)).thenReturn(resumeList);

        ArgumentCaptor<Resume> resumeCaptor = ArgumentCaptor.forClass(Resume.class);
        when(resumeRepository.save(resumeCaptor.capture())).thenAnswer(invocation -> {
            Resume capturedResume = resumeCaptor.getValue();
            return capturedResume;
        });
        ResumesResponse response = resumesService.patchResume(username, resumeId, request);

        assertEquals(titleAlteration, response.getTitle());
        assertEquals(descritionAlteration, response.getDescription());
        assertEquals(resumeId, response.getId());
    }

    @Test
    public void testGetResumes_sucess() {
        String username = "Test";
        Integer resumeId = 1;
        String title = "title";
        String description = "description";

        Resume resume = new Resume(title, description);
        resume.setId(resumeId);

        List<Resume> resumeList = new ArrayList<>();
        resumeList.add(resume);

        when(resumeRepository.findAllByUserUsername(username)).thenReturn(resumeList);

        List<ResumesResponse> response = resumesService.getUserResumes(username);
        assertEquals(title, response.get(0).getTitle());
        assertEquals(description, response.get(0).getDescription());
        assertEquals(resumeId, response.get(0).getId());

    }
}
