package com.mhere.user.service.user;

import com.mhere.auth.UserTransfer;
import com.mhere.base.AbstractDomainModelService;
import com.mhere.base.MultiDomainModelServiceFactory;
import com.mhere.user.service.user.entity.UserEntity;
import com.mhere.user.service.user.entity.UserInfoEntity;

public class UserFactory {

    private static final String SERVICE_KEY = "USER";

    private static UserService getService() {
        AbstractDomainModelService abstractDomainModelService = MultiDomainModelServiceFactory.getDomainModelService(SERVICE_KEY);

        assert abstractDomainModelService instanceof UserService;
        return (UserService) abstractDomainModelService;
    }

    public static User create() {
        UserService service = getService();
        UserEntity userEntity = new UserEntity();
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        return new User(service, userEntity, userInfoEntity);
    }

    public static User create(String id) {
        UserService service = getService();
        UserEntity entity = service.getEntity(id);
        UserInfoEntity infoEntity = service.getInfoEntity(id);
        return new User(service, entity, infoEntity);
    }

    public static User create(UserTransfer transfer) {
        User user = create();
        user.setName(transfer.getName());


        return user;
    }

}

