package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class projectConfig {

    @Bean
    public RestTemplate restTemplate( ) {
        return new RestTemplate();
    }

    @Bean(name = "application-json-header")
    public HttpHeaders applicationJsonHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Bean(name = "application-multipart-header")
    public HttpHeaders applicationMultipartHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    @Bean
    public ObjectMapper registerObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }
}

