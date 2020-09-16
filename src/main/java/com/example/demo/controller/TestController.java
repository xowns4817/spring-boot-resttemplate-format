package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.model.TestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("application-json-header")
    private HttpHeaders jsonHeaders;

    @Autowired
    @Qualifier("application-multipart-header")
    private HttpHeaders multipartHeaders;

    @Autowired
    private ObjectMapper objectMapper;
    
    @GetMapping("/test")
    public Object get_test( ) {

        log.info("[GET] /test ");

        HttpEntity<TestDto> entity = new HttpEntity<TestDto>(jsonHeaders);

        TestDto testDto = restTemplate.exchange("http://192.168.0.17:7777/test", HttpMethod.GET, entity, TestDto.class).getBody();
        return testDto;
    };

    @PostMapping("/test")
    public void post_test(@RequestBody TestDto testDto) {

        log.info("[POST] /test ");

        HttpEntity<TestDto> entity = new HttpEntity<TestDto>(testDto, jsonHeaders);
        restTemplate.exchange("http://192.168.0.17:7777/test", HttpMethod.POST, entity, TestDto.class);
    }


    @PutMapping("/test")
    public void put_test(@RequestBody TestDto testDto ) {

        log.info("[PUTT] /test ");

        HttpEntity<TestDto> entity = new HttpEntity<TestDto>(testDto, jsonHeaders);
        restTemplate.exchange("http://192.168.0.17:7777/test", HttpMethod.PUT, entity, TestDto.class).getBody();
    }

    @DeleteMapping("/test/{id}")
    public void delete_test(@PathVariable("id") String id) {

        log.info("[DELETE] /test ");

        HttpEntity<TestDto> entity = new HttpEntity<TestDto>(jsonHeaders);
        restTemplate.exchange("http://192.168.0.17:7777/test", HttpMethod.DELETE, entity, TestDto.class).getBody();

    }

    @PostMapping("/test/file")
    public void post_multipart_test(@RequestParam("my-file") MultipartFile multipartFile,
                                    @RequestParam("key") String key) {

        log.info("[POST] /file/test ");
        log.info("key : " + key);
        log.info("file name : " + multipartFile.getOriginalFilename());
        log.info("file size : " + multipartFile.getSize());

        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("key", key);
        bodyMap.add("my-file", multipartFile.getResource());


        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, multipartHeaders);
        restTemplate.postForEntity("http://192.168.0.17:7777/test/file", requestEntity, String.class);
    }
}
