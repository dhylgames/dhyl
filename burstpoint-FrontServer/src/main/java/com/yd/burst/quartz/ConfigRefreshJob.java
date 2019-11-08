package com.yd.burst.quartz;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.util.ConfigUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-05 22:45
 **/
public class ConfigRefreshJob {

    private static Logger logger = LogManager.getLogger(ConfigRefreshJob.class);

    @Autowired
    private CacheBase cacheBase;

    public void execute(){
//        logger.info("刷新全局配置缓存" + new Date());
//        cacheBase.refreshAllConfig();
        cacheBase.refreshNpcPlayer();
    }
}
