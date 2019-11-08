package com.yd.burst.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description: 游戏初始化线程
 * @Author: Will
 * @Date: 2019-08-09 18:28
 **/
public class ContextListener implements ServletContextListener {

    private static Logger logger = LogManager.getLogger(ContextListener.class);
    @Override
    public void contextDestroyed(ServletContextEvent e) {
    }


    @Override
    public void contextInitialized(ServletContextEvent e) {
        //new Thread(new GameContextThread()).start();
        logger.info("-------游戏初始化线程启动--------");
    }

}
