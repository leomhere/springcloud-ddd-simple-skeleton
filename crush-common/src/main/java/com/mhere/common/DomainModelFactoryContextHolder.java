package com.mhere.common;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DomainModelFactoryContextHolder {

    private static Map<String, AbstractDomainModelService> DOMAIN_MODEL_FACTORY_SERVICE_CONTEXT_HOLDER  = new ConcurrentHashMap<String, AbstractDomainModelService>();

    static void registerDomainModelService(AbstractDomainModelService service) {
        DOMAIN_MODEL_FACTORY_SERVICE_CONTEXT_HOLDER.put(service.type(), service);
    }

    public static AbstractDomainModelService getDomainModelService(String type) {
        AbstractDomainModelService abstractDomainModelService = DOMAIN_MODEL_FACTORY_SERVICE_CONTEXT_HOLDER.get(type);
        assert Objects.nonNull(abstractDomainModelService);
        return abstractDomainModelService;
    }
}
