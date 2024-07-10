package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.Request.ResumesRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.ResumesResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.LearnTools.LearnToolsApi.model.entidades.Resume;
import com.LearnTools.LearnToolsApi.services.ResumesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/resumes")
public class ResumesController {
    private final ResumesService resumesService;

    public ResumesController(ResumesService resumesService) {
        this.resumesService = resumesService;
    }

    @GetMapping()
    public ResponseEntity<List<ResumesResponse>> getMethodName(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Integer id) {
        List<ResumesResponse> userResumesFormatted = resumesService.getUserResumes(userDetails);
        return ResponseEntity.ok(userResumesFormatted);
    }

    @DeleteMapping("/{resumeID}")
    @Transactional
    public ResponseEntity<?> deleteResume(@AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer resumeID) {
        resumesService.deleteResume(userDetails, resumeID);

        return ResponseEntity.ok().build();
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Resume> postResume(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ResumesRequest resumesDTO) {

        return ResponseEntity.ok(resumesService.createResume(resumesDTO, userDetails));
    }

}
