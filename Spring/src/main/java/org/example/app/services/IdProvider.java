package org.example.app.services;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    private Logger logger = Logger.getLogger(IdProvider.class);

    public Integer provideId(Object object) {
        return object.hashCode();
    }

    private void initIdProvider() {
        logger.info("provider INIT");
    }

    private void destroyIdProvider() {
        logger.info("provider DESTROY");
    }

    private void defaultInit() {
        logger.info("default INIT in provider");
    }

    private void defaultDestroy() {
        logger.info("default DESTROY in provider");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("provider after afterPropertiesSet() from InitializingBean invoked");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("provider after destroy() from DisposableBean invoked");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessBeforeInitialization from BeanPostProcessor invoked by bean " + beanName);
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("postProcessAfterInitialization from BeanPostProcessor invoked by bean " + beanName);
        return null;
    }

    @PostConstruct
    public void postConstructProvider() {
        logger.info("PostConstruct annotated method invoked");
    }

    @PreDestroy
    public void preDestroyProvider() {
        logger.info("PreDestroy annotated method invoked");
    }

}
