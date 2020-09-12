package com.mhere.base;

import javax.annotation.PostConstruct;

abstract
public class AbstractDomainModelService {

    @PostConstruct
    public void init() {
        MultiDomainModelServiceFactory.registerDomainModelService(this);
    }

    abstract protected String type();
}
