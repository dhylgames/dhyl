package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
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
     * 群成员列表信息
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
    @RequestMapping("/addGroup")
    public  Result  addGroup(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        String  userId=params.get("userId");
        if (StringUtils.isEmpty(groupCode)||StringUtils.isEmpty(userId)) {
            throw new ValidationException();
        }
        GroupUser  groupUser=new GroupUser();
        groupUser.setGroupCode(groupCode);
        groupUser.setGroupUserId(userId);
        groupUser.setGroupUserStatus("0");
        groupUser.setGroupUserType("1");
        ICode str =   groupUserService.addGroupUser(groupUser);
        if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
            return Result.success();
        } else {
            return Result.fail(str);
        }
    }
}
