package com.LearnTools.LearnToolsApi.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.LearnTools.LearnToolsApi.client")
public class FeignConfig {

}
