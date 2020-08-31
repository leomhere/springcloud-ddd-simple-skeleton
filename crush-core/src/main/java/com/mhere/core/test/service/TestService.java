package com.mhere.core.test.service;

import com.mhere.common.transfer.UserTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    @Autowired
    private UserApi userApi;

    public Mono<UserTransfer> getUser() {
        return userApi.get("test");
    }
}
