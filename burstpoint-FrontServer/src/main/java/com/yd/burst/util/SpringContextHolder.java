/**
 * Copyright (c) 2011 Vanda Group.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * <p>
 * $Id: SpringContextHolder.java 1468 2011-02-10 15:22:48Z calvinxiu $
 */
package com.yd.burst.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.io.IOException;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候取出ApplicaitonContext.
 *
 * @author calvin
 */
public class SpringContextHolder implements ApplicationContextAware,
        DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory
            .getLogger(SpringContextHolder.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringContextHolder:"
                + applicationContext);

        if (SpringContextHolder.applicationContext != null) {
            logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext; // NOSONAR
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        logger.debug("清除SpringContextHolder中的ApplicationContext:"
                + applicationContext);
        applicationContext = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        AssertUtils
                .state(applicationContext != null,
                        "applicationContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder.");
    }

    /**
     * <pre>
     * 功能: 获得web项目物理路径
     * </pre>
     *
     * @return String
     * 创建时间:Oct 24, 2011 10:05:15 AM
     */
    public static String getWebRootFilePath() {
        try {
            return applicationContext.getResource("").getFile().getAbsolutePath() + File.separator;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void testShowAllBeanNames() {
        try {
            System.out.println("COUNT --> " + getApplicationContext().getBeanDefinitionNames().length);
            for (String n : getApplicationContext().getBeanDefinitionNames()) {
                System.out.println(n);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextHolder.clearHolder();
    }
}
