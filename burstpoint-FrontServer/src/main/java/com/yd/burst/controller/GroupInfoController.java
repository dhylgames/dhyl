package com.yd.burst.controller;

import com.yd.burst.cache.CacheKey;
import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.GroupUser;
import com.yd.burst.model.RoomInfo;
import com.yd.burst.model.User;
import com.yd.burst.service.GroupInfoService;
import com.yd.burst.service.GroupUserService;
import com.yd.burst.util.Constants;
import com.yd.burst.util.JWTUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/groupInfo")
public class GroupInfoController {
    private static Logger logger = LogManager.getLogger(GroupInfoController.class);

    @Autowired
    private GroupInfoService  groupInfoService;
    @Autowired
    private GroupUserService   groupUserService;
    /**
     * 群列表信息
     * @param params
     * @return
     */
    @RequestMapping("/findGroupInfo")
    public Result  findGroupInfo(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String phone = params.get("phone");
        if (StringUtils.isEmpty(phone)) {
            throw new ValidationException();
        }
        Object   object =groupUserService.getGroupUser(phone);
        if (object instanceof List) {
            Map  map=new HashMap();
            List<GroupUser> groupUsers = (List<GroupUser>) object;
            return Result.success(groupUsers);
        } else {
            return Result.fail(CodeEnum.ERROR);
        }
    }

    /**
     * 解散群
     * @param params
     * @return
     */
    @RequestMapping("/disBandGroup")
    public Result disBandGroupOrExit(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        if (StringUtils.isEmpty(groupCode)) {
            throw new ValidationException();
        }
        ICode code=   groupInfoService.disBandGroup(groupCode);
        if (CodeEnum.SUCCESS.getCode().equals(code.getCode())) {
            return Result.success();
        } else {
            return Result.fail(code);
        }
    }
    /**
     * 退出群
     * @param params
     * @return
     */
    @RequestMapping("/exitGroup")
    public Result exitGroup(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        String userId = params.get("userId");
        if (StringUtils.isEmpty(userId)||StringUtils.isEmpty(groupCode)) {
            throw new ValidationException();
        }
        ICode code= groupUserService.exitGroup(userId,groupCode);
        if (CodeEnum.SUCCESS.getCode().equals(code.getCode())) {
            return Result.success();
        } else {
            return Result.fail(code);
        }

    }

    /**
     * 获取群房间信息
     * @param params
     * @return
     */
    @RequestMapping("/getGroupRoomInfo")
    public Result getGroupRoomInfo(@RequestBody Map<String, String> params){
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String groupCode = params.get("groupCode");
        if (StringUtils.isEmpty(groupCode)) {
            throw new ValidationException();
        }
        List<RoomInfo> list = groupInfoService.getGroupRoomInfo(CacheKey.GROUP_KEY+groupCode);
        return Result.success(list);
    }

}
