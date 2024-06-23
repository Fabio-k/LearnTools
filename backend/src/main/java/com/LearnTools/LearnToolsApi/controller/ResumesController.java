package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.ResumesDTO;
import com.LearnTools.LearnToolsApi.controller.dto.ResumesResponseDTO;
import com.LearnTools.LearnToolsApi.handler.BusinessException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.model.entidades.ResumeTag;
import com.LearnTools.LearnToolsApi.model.entidades.Tag;
import com.LearnTools.LearnToolsApi.model.repository.ResumeRepository;
import com.LearnTools.LearnToolsApi.model.repository.ResumeTagRepository;
import com.LearnTools.LearnToolsApi.model.repository.TagRepository;
import com.LearnTools.LearnToolsApi.model.repository.UserRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/resumes")
public class ResumesController {
    private final ResumeTagRepository resumeTagRepository;
    private final ResumeRepository resumeRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public ResumesController(ResumeTagRepository resumeTagRepository, ResumeRepository resumeRepository,
            TagRepository tagRepository, UserRepository userRepository) {
        this.resumeTagRepository = resumeTagRepository;
        this.resumeRepository = resumeRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
    }

    @GetMapping()
    public List<ResumesResponseDTO> getMethodName(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Integer id) {
        List<Resume> userResumes = getUserResumes(userDetails);
        return userResumes.stream().map(ResumesResponseDTO::fromEntity).collect(Collectors.toList());
    }

    @DeleteMapping("/{resumeID}")
    @Transactional
    public void deleteResume(@AuthenticationPrincipal UserDetails userDetails, Integer resumeID) {
        List<Resume> userResumes = getUserResumes(userDetails);
        Optional<Resume> matchResume = userResumes.stream().filter(r -> r.getId() == resumeID).findFirst();
        resumeRepository.delete(matchResume.get());
    }

    private List<Resume> getUserResumes(UserDetails userDetails) {
        String username = userDetails.getUsername();
        return resumeRepository.findAllByUserUsername(username);
    }

    @PostMapping()
    @Transactional
    public void postResume(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ResumesDTO resumesDTO) {
        Resume resume = new Resume(resumesDTO.getTitle(), resumesDTO.getResume());
        resume.setUser(userRepository.findByUsername(userDetails.getUsername()));
        resumeRepository.save(resume);
        createResumeTags(resumesDTO, userDetails, resume);
    }

    private void createResumeTags(ResumesDTO resumesDTO, UserDetails userDetails, Resume resume) {
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
}
