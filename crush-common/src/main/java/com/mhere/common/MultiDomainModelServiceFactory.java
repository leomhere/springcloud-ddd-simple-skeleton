package com.mhere.common;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MultiDomainModelServiceFactory {

    private static Map<String, AbstractDomainModelService> multiDomainModelServiceHolder = new ConcurrentHashMap<String, AbstractDomainModelService>();

    static void registerDomainModelService(AbstractDomainModelService service) {
        multiDomainModelServiceHolder.put(service.type(), service);
    }

    public static AbstractDomainModelService getDomainModelService(String type) {
        AbstractDomainModelService abstractDomainModelService = multiDomainModelServiceHolder.get(type);
        assert Objects.nonNull(abstractDomainModelService);
        return abstractDomainModelService;
    }
}
