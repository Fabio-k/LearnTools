package com.LearnTools.LearnToolsApi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.LearnTools.LearnToolsApi.controller.dto.Request.GithubAcessTokenRequest;
import com.LearnTools.LearnToolsApi.controller.dto.Response.GithubTokenResponse;

@FeignClient(name = "githubWebClient", url = "https://github.com/login/oauth")
public interface GithubWebClient {

    @PostMapping(value = "/access_token")
    GithubTokenResponse getToken(@RequestHeader("Accept") String accept, @RequestBody GithubAcessTokenRequest request);
}
