package com.yd.burst.common;

import com.yd.burst.enums.UserStatusEnum;
import com.yd.burst.model.Player;
import com.yd.burst.service.PlayerService;
import com.yd.burst.util.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 16:53
 **/
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private PlayerService playerService;

    /**
     * 权限校验
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.decode(principals.toString());
        Player player = playerService.findByName(username);
        if (player == null) {
            throw new UnknownAccountException();
        }
        if (UserStatusEnum.STOP.getCode().equals(player.getUserStatus())) {
            throw new LockedAccountException();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRole(player.getPlayerName());
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String id = JWTUtil.decode(token);
        if (id == null) {
            // Token解密失败，抛出异常
            throw new AuthenticationException("Invalid token.");
        }
        // Token解密成功，返回SimpleAuthenticationInfo对象
        return new SimpleAuthenticationInfo(token, token, "myRealm");
    }
}