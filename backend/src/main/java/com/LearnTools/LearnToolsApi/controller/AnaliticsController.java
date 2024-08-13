package com.LearnTools.LearnToolsApi.controller;

import org.springframework.web.bind.annotation.RestController;

import com.LearnTools.LearnToolsApi.controller.dto.Response.AnaliticsResponse;
import com.LearnTools.LearnToolsApi.services.AnaliticsService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/analitics")
public class AnaliticsController {
    private final AnaliticsService analiticsService;

    public AnaliticsController(AnaliticsService analiticsService) {
        this.analiticsService = analiticsService;
    }

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<AnaliticsResponse> getFlashcardsAnalitics(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        return ResponseEntity.ok(analiticsService.getAnalitics(username));
    }
}
