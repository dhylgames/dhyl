package com.yd.burst.common;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-31 19:56
 **/
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
