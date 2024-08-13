package com.LearnTools.LearnToolsApi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.controller.dto.Request.ResumesRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ResumesResponse;
import com.LearnTools.LearnToolsApi.handler.BusinessException;
import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.model.entidades.ResumeTag;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.entidades.User;
import com.LearnTools.LearnToolsApi.model.repository.ResumeRepository;
import com.LearnTools.LearnToolsApi.model.repository.ResumeTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;

@Service
public class ResumesService {

    private final ResumeRepository resumeRepository;
    private final TagRepository tagRepository;
    private final ResumeTagRepository resumeTagRepository;

    private final UserService userService;

    public ResumesService(ResumeRepository resumeRepository, TagRepository tagRepository,
            ResumeTagRepository resumeTagRepository, UserService userService) {

        this.resumeRepository = resumeRepository;
        this.tagRepository = tagRepository;
        this.resumeTagRepository = resumeTagRepository;
        this.userService = userService;
    }

    public ResumesResponse createResume(ResumesRequest resumesDTO, String username) {
        Resume resume = new Resume(resumesDTO.getTitle(), resumesDTO.getDescription());
        User user = userService.getUser(username);
        resume.setUser(user);
        Resume resumeSaved = resumeRepository.save(resume);

        ResumesResponse resumesResponse = new ResumesResponse();
        resumesResponse.setTitle(resumeSaved.getTitle());
        resumesResponse.setDescription(resumeSaved.getDescription());
        resumesResponse.setId(resumeSaved.getId());
        return resumesResponse;

    }

    private void createResumeTags(ResumesRequest resumesDTO, UserDetails userDetails, Resume resume) {
        List<Tag> userTags = tagRepository.findAllByUserUsername(userDetails.getUsername());
        if (userTags == null)
            throw new BusinessException("necessário incuir pelo menos uma tag");
        for (String name : resumesDTO.getTagName()) {
            Optional<Tag> matchTag = userTags.stream().filter(t -> t.getName().equals(name)).findFirst();
            if (matchTag.isEmpty())
                throw new BusinessException("Tag não encontrada");

            ResumeTag resumeTag = new ResumeTag(resume, matchTag.get());
            resumeTagRepository.save(resumeTag);
        }
    }

    public void deleteResume(String username, Integer resumeID) {
        if (username == null)
            throw new BusinessException("username cannot be null");
        if (resumeID == null)
            throw new BusinessException("resumeId cannot be null");
        List<Resume> userResumes = findUserResumes(username);
        Optional<Resume> matchResume = userResumes.stream().filter(r -> r.getId() == resumeID).findFirst();
        if (matchResume.isEmpty())
            throw new BusinessException("resume not found");
        resumeRepository.delete(matchResume.get());
    }

    public List<ResumesResponse> getUserResumes(String username) {
        List<Resume> userResumes = findUserResumes(username);
        return userResumes.stream().map(ResumesResponse::fromEntity).collect(Collectors.toList());
    }

    public ResumesResponse patchResume(String username, Integer resumeId, ResumesRequest resumesRequest) {
        List<Resume> userResumes = findUserResumes(username);
        Optional<Resume> OptionalResume = userResumes.stream().filter(r -> r.getId() == resumeId).findFirst();
        if (OptionalResume.isEmpty())
            throw new BusinessException("resume not found");
        Resume resume = OptionalResume.get();
        if (resumesRequest.getTitle() != null)
            resume.setTitle(resumesRequest.getTitle());
        if (resumesRequest.getDescription() != null)
            resume.setDescription(resumesRequest.getDescription());

        Resume savedResume = resumeRepository.save(resume);

        ResumesResponse resumeResponse = new ResumesResponse();
        resumeResponse.setTitle(savedResume.getTitle());
        resumeResponse.setDescription(savedResume.getDescription());
        resumeResponse.setId(savedResume.getId());
        return resumeResponse;
    }

    private List<Resume> findUserResumes(String username) {
        return resumeRepository.findAllByUserUsername(username);
    }
}
