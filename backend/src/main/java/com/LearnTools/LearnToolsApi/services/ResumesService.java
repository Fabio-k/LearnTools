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

    public Resume createResume(ResumesRequest resumesDTO, UserDetails userDetails) {
        Resume resume = new Resume(resumesDTO.getTitle(), resumesDTO.getResume());
        User user = userService.getUser(userDetails.getUsername());
        resume.setUser(user);
        Resume resumeSaved = resumeRepository.save(resume);
        createResumeTags(resumesDTO, userDetails, resume);
        return resumeSaved;

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

    public void deleteResume(UserDetails userDetails, Integer resumeID) {
        List<Resume> userResumes = findUserResumes(userDetails);
        Optional<Resume> matchResume = userResumes.stream().filter(r -> r.getId() == resumeID).findFirst();
        if (matchResume.isEmpty())
            throw new BusinessException("resume not found");
        resumeRepository.delete(matchResume.get());
    }

    public List<ResumesResponse> getUserResumes(UserDetails userDetails) {
        List<Resume> userResumes = findUserResumes(userDetails);
        return userResumes.stream().map(ResumesResponse::fromEntity).collect(Collectors.toList());
    }

    private List<Resume> findUserResumes(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return resumeRepository.findAllByUserUsername(username);
    }
}
