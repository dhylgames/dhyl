package com.yd.burst.aop;

import com.yd.burst.cache.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-05 12:08
 **/
@Aspect
@Component
@Order(5)
public class RedisAspect {
    private static final Logger logger = LoggerFactory.getLogger(RedisAspect.class);
    @Autowired
    private RedisUtil redisUtil;

    // Controller层切点
    @Pointcut("@annotation(com.yd.burst.annotation.Redis)") //@annotation用于匹配当前执行方法持有指定注解的方法；
    public void redisAspect() {
    }

    @Around("redisAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取目标方法参数
        Object[] args = joinPoint.getArgs();
        String applId = null;
        if (args != null && args.length > 0) {
            applId = String.valueOf(args[0]);
        }

        // 请求类名称
        String targetName = joinPoint.getTarget().getClass().getName();
        // 请求方法
        String methodName = joinPoint.getSignature().getName();

        //redis中key格式：请求类名称 + 请求方法 + 目标方法参数
        String redisKey = targetName + methodName + applId;
        logger.debug("调用从redis中查询的方法，redisKey=" + redisKey);
        //获取从redis中查询到的对象
        Object objectFromRedis = redisUtil.getData4Object2Redis(redisKey);

        //如果查询到了
        if(null != objectFromRedis){
            logger.debug("从redis中查询到了数据...不需要查询数据库");
            return objectFromRedis;
        }

        logger.debug("没有从redis中查到数据...");

        //没有查到，那么查询数据库
        Object object = null;
        object = joinPoint.proceed();
        //后置：将数据库中查询的数据放到redis中
        logger.debug("把数据库查询的数据存储到redis中的方法...");

        redisUtil.setData4Object2Redis(redisKey, object);
        //将查询到的数据返回
        return object;
    }
}
