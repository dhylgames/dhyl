package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.model.GroupUser;
import com.yd.burst.model.User;
import com.yd.burst.service.GroupUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/groupUser")
public class GroupUserController {
    private static Logger logger = LogManager.getLogger(GroupUserController.class);
    @Autowired
    private GroupUserService  groupUserService;

    /**
     * 根据群号查找当前群的所有成员信息
     */
    @RequestMapping("/findGroupUser")
    public Result   findGroupUser(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        if (StringUtils.isEmpty(groupCode)) {
            throw new ValidationException();
        }
       Object  object=groupUserService.findGroupUsers(groupCode);
        if (object !=null) {
            List<User> Users = (List<User>) object;
            return Result.success(Users);
        } else {
            return Result.fail(CodeEnum.ERROR);
        }
    }

}
