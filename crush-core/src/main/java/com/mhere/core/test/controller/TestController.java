package com.mhere.core.test.controller;

import com.mhere.common.transfer.UserTransfer;
import com.mhere.core.test.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;


    @GetMapping("/user")
    private Mono<UserTransfer> getUser() {
        return testService.getUser();
    }
}
