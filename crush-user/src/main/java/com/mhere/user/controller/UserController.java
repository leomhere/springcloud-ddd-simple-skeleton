package com.mhere.user.controller;


import com.mhere.auth.UserTransfer;
import com.mhere.user.service.user.User;
import com.mhere.user.service.user.UserFactory;
import com.mhere.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    private UserTransfer get(@PathVariable String id) {
        User user = UserFactory.create(id);
        return user2Transfer(user);
    }

    @PostMapping
    private void post(@RequestBody UserTransfer transfer) {
        User user = UserFactory.create(transfer);
        service.save(user);
    }



    private UserTransfer user2Transfer(User user) {
        UserTransfer transfer = new UserTransfer();
        transfer.setName(user.getName());

        return transfer;
    }

}
