package com.mhere.user.service.user;

import com.mhere.user.service.user.entity.UserEntity;
import com.mhere.user.service.user.entity.UserInfoEntity;

public class User {

    private UserService service;
    private UserEntity entity;
    private UserInfoEntity infoEntity;

    public User(UserService service, UserEntity entity, UserInfoEntity infoEntity) {
        this.service = service;
        this.entity = entity;
        this.infoEntity = infoEntity;
    }

    UserEntity entity() {
        return this.entity;
    }

    UserInfoEntity infoEntity() {
        return this.infoEntity;
    }

    public String getId() {
        return entity.getId();
    }

    public void setId(String id) {
        entity.setId(id);
        infoEntity.setId(id);
    }

    public String getName() {
        return infoEntity.getName();
    }

    public void setName(String name) {
        infoEntity.setName(name);
    }

    public String getMobile() {
        return entity.getMobile();
    }

    public void setMobile(String mobile) {
        entity.setMobile(mobile);
    }

    public void setPassword(String password) {
        entity.setPassword(password);
    }

}
