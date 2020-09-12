package com.mhere.user.service.user;

import com.mhere.base.AbstractDomainModelService;
import com.mhere.base.error.Errors;
import com.mhere.user.service.user.entity.UserEntity;
import com.mhere.user.service.user.entity.UserInfoEntity;
import com.mhere.user.service.user.entity.UserInfoRepository;
import com.mhere.user.service.user.entity.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService extends AbstractDomainModelService {

    @Autowired
    private UserRepository userRepository;
    private UserInfoRepository userInfoRepository;

    @Override
    protected String type() {
        return "USER";
    }

    UserEntity getEntity(String id) {

        return userRepository.findById(id)
                .orElseThrow(Errors.UN_AUTHORIZED::create);
    }

    UserInfoEntity getInfoEntity(String id) {

        return userInfoRepository.findById(id)
                .orElseThrow(Errors.UN_AUTHORIZED::create);
    }

    public String save(User user) {
        assert Objects.nonNull(user);

        return StringUtils.isBlank(user.getId())
                ? register(user)
                : update(user);

    }

    private String register(User user) {
        assert StringUtils.isNotBlank(user.getMobile());

        user.setId(UUID.randomUUID().toString().replaceAll("-", ""));
//        user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        userRepository.save(user.entity());
        userInfoRepository.save(user.infoEntity());
        return user.getId();
    }

    private String update(User user) {

        //

        return user.getId();
    }
}
