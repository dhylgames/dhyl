package com.yd.burst.controller;

import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.model.GroupUser;
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
        if (object !=null) {
            List<GroupUser> groupUser = (List<GroupUser>) object;
            return Result.success(groupUser);
        } else {
            return Result.fail(CodeEnum.ERROR);
        }
    }


}
