package com.yd.burst.filter;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yd.burst.common.RequestWrapper;
import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.model.Player;
import com.yd.burst.model.User;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.util.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-31 20:46
 **/
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
        String path = servletRequest.getRequestURI();
        User player = (User) session.getAttribute(Constants.SESSION_KEY);
        RequestWrapper wrapper = new RequestWrapper(servletRequest);
        String param = wrapper.getBodyString();
        String playerIdTemp = null;
        if (param != null) {
            JSONObject object = JSONObject.parseObject(param);
            playerIdTemp = object == null ? null : object.getString("playerId");
        }
        if (path.indexOf("/login") > -1) {
            chain.doFilter(wrapper, servletResponse);
            return;
        }
        if (StringUtils.isNotBlank(playerIdTemp)) {
            if (player == null) {
                throw new ValidationException();
            }
            String id = String.valueOf(player.getId());
            if (player != null && !id.equals(playerIdTemp)) {
                servletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                Result result = Result.fail(CodeEnum.ERROR);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(convertObjectToJson(result));
                return;
            } else {
                chain.doFilter(wrapper, response);
            }
        } else {
            chain.doFilter(wrapper, response);
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
