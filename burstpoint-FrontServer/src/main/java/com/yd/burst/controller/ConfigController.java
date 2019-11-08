package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.util.ConfigUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-13 20:40
 **/
@RestController
@RequestMapping("api/conf")
public class ConfigController {

    /**
     * 刷新配置参数（需要在后台修改配置后对该接口进行调用以修改配置参数）
     * @return
     */
    @RequestMapping("/cc")
    public Result refreshConfig(){
        ConfigUtil.reload();
        return Result.success();
    }
}
