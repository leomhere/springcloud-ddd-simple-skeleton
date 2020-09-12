package com.mhere.utils.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(SpringContextHolder.class);
    private static ApplicationContext applicationContext = null;

    public SpringContextHolder() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    @Override
    public void setApplicationContext(@Nullable ApplicationContext _applicationContext) throws BeansException {
        log.info("注入ApplicationContext到SpringContextHolder:{}" + _applicationContext);
        if (applicationContext != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:" + applicationContext);
        }

        applicationContext = _applicationContext;
    }

    @Override
    public void destroy() {
        clearHolder();
    }

    public static void clearHolder() {
        log.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }
}
