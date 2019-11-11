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
import java.util.HashMap;
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
        GroupUser groupUser=new GroupUser();
        groupUser.setGroupCode(groupCode);
        groupUser.setGroupUserStatus("1");
       Object  object=groupUserService.findGroupUsers(groupUser);
        if (object instanceof List) {
            List<User> Users = (List<User>) object;
            return Result.success(Users);
        } else {
            return Result.fail((CodeEnum) object);
        }
    }

    /**
     * 增加群
     * @param params
     * @return
     */
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
        groupUser.setGroupUserType("0");
        ICode str =   groupUserService.addGroupUser(groupUser);
        if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
            return Result.success();
        } else {
            return Result.fail(str);
        }
    }

    /**
     * 审核信息列表
     * @param params
     * @return
     */
    @RequestMapping("/auditInfoList")
    public Result  auditInfoList(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        if (StringUtils.isEmpty(groupCode)) {
            throw new ValidationException();
        }
        GroupUser groupUser=new GroupUser();
        groupUser.setGroupCode(groupCode);
        groupUser.setGroupUserStatus("0");
        groupUser.setGroupUserType("0");
        Object  object=groupUserService.findGroupUsers(groupUser);
        if (object instanceof List) {
            Map map=new HashMap<Object,Object>();
            List<User> Users = (List<User>) object;
            map.put("groupCode",groupCode);
            map.put("users",Users);
            return Result.success(map);
        } else {
            return Result.fail((CodeEnum) object);
        }
    }
    /**
     * 审核同意和拒绝
     * @param params
     * @return
     */
    @RequestMapping("/auditUser")
    public Result  auditUser(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        String status = params.get("status");
        String  userId=params.get("userId");
        if (StringUtils.isEmpty(groupCode)||StringUtils.isEmpty(status)||StringUtils.isEmpty(userId)) {
            throw new ValidationException();
        }
      ICode  code=  groupUserService.auditUser(userId,status,groupCode);
        if (CodeEnum.SUCCESS.getCode().equals(code.getCode())) {
            return Result.success();
        } else {
            return Result.fail(code);
        }
    }
}
