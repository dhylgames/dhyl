package com.yd.burst.webSkt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: kelly
 * @Date: 2019/11/20 00:54
 * @Description:
 */
@Configuration
public class MyConfigure
{

    @Bean
    public MyEndpointConfigure newConfigure()
    {
        return new MyEndpointConfigure();
    }
}
