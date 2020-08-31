package com.mhere.core.test.service;

import com.mhere.common.http.HttpClient;
import com.mhere.common.http.ResponseSpec;
import com.mhere.common.transfer.UserTransfer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserApi {

    private static final String USER_SERVER_URL = "lb:/crush-user/";


    public Mono<UserTransfer> get(String id) {
        return HttpClient
                .create()
                .uri(USER_SERVER_URL + "/user/" + id)
                .async()
                .get(UserTransfer.class)
                .map(ResponseSpec.ResponseOperationSpec::body);
    }



}
