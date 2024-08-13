package com.LearnTools.LearnToolsApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.LearnTools.LearnToolsApi.controller.dto.Client.GithubUserResponse;

@FeignClient(name = "githubClient", url = "https://api.github.com")
public interface GithubApiClient {

    @GetMapping("/user")
    GithubUserResponse getUserInformation(@RequestHeader("Authorization") String authToken);

}
