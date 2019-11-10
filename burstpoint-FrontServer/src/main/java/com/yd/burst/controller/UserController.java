package com.yd.burst.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.yd.burst.cache.RedisUtil;
import com.yd.burst.common.Result;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.User;
import com.yd.burst.service.UserService;
import com.yd.burst.util.Constants;
import com.yd.burst.util.JWTUtil;
import com.yd.burst.util.Md5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:59
 **/
@RestController
@RequestMapping("api/user")
public class UserController {

    private static Logger logger = LogManager.getLogger(UserController.class);
    private static DefaultKaptcha KAPTCHA_PRODUCER;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    static {
        KAPTCHA_PRODUCER = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.image.width", "80");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.session.key", "KAPTCHA_VERIFY_CODE");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "Arial");
        properties.setProperty("kaptcha.textproducer.char.string", "ACEFHJKLMNPQRStWXYZ234579");
        properties.setProperty("kaptcha.textproducer.font.size", "24");

        Config config = new Config(properties);
        KAPTCHA_PRODUCER.setConfig(config);
    }

    /**
     * 玩家注册
     *
     * @return
     */
    @RequestMapping("/register")
    public Result register(@RequestBody Map<String, String> params) {
        String code=redisUtil.get(params.get("phone"));
        if(params.get("code").equals(code))
        {
            User user = new User();
            if (params != null && params.size() == 3) {
                user.setPassword(params.get("password"));
                user.setPhone(params.get("phone"));
            } else {
                return Result.fail(CodeEnum.REGISTER_FAILED);
            }
            ICode str = userService.register(user);
            if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
                return Result.success(user);
            } else {
                return Result.fail(str);
            }
        }else{
            return Result.fail(CodeEnum.VERIFICATION_FAILED);
        }
    }

    /**
     * 玩家密码登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login_pass")
    public Result login_pass(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {

        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String phone = params.get("phone");
        String password = params.get("password");
        //String code = params.get("code");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            throw new ValidationException();
        }

        HttpSession session = request.getSession();
        /*
         * 验证码校验
         */
    /*    if (!WebUtil.checkVerifyCode(request, code)) {
            request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
            return Result.fail(CodeEnum.VERIFICATION_FAILED);
        }*/
        Object object = userService.login(phone,password);
        if (object instanceof User) {
            Map map=new HashMap<Object,Object>();
            User user = (User) object;
            session.setAttribute(Constants.SESSION_KEY, user);
            String token=JWTUtil.encode(user.getPhone(),12*60);
            map.put("token",token);
            map.put("user",user);
            return Result.success(map);
        } else {
            return Result.fail((CodeEnum) object);
        }
    }




    /**
     * 玩家手机号登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login_phone")
    public Result login_phone(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {

        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String phone = params.get("phone");
        String code = params.get("code");
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)) {
            throw new ValidationException();
        }

        if(code.equals(redisUtil.get(params.get("phone"))))
        {
            User user = new User();
            user.setPhone(phone);
            HttpSession session = request.getSession();
            Object object = userService.login(user.getPhone());
            if (object instanceof User) {
                Map map=new HashMap<Object,Object>();
                User player = (User) object;
                session.setAttribute(Constants.SESSION_KEY, player);
                String token=JWTUtil.encode(player.getPhone(),12*60);
                map.put("token",token);
                map.put("user",user);
                return Result.success(map);
            } else {
                return Result.fail((CodeEnum) object);
            }
        }
         else{
            return Result.fail(CodeEnum.VERIFICATION_FAILED);
        }
    }

    /**
     * 刷新验证码
     *
     * @param response
     * @param session
     * @throws Exception
     */
    @RequestMapping("/code")
    public void getKaptchaImage(HttpServletResponse response, HttpSession session) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = KAPTCHA_PRODUCER.createText();
        session.setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = KAPTCHA_PRODUCER.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }



    /**
     * 发送短信验证
     *
     * @return
     */
    @RequestMapping("/smsVerify")
    public Result smsVerify(@RequestBody Map<String, String> params) {

        if (StringUtils.isNotBlank(params.get("phone")))
        {
            Integer num = (int)(1+Math.random()*999999);
            String paramItem="appid=42212&to="+params.get("phone")+"&content=【大恒国际】您的验证码是："+num+"，请在5分钟内输入&signature=c6d7c7266a296f798ffc5b6067586cc9";
            JSONObject item = JSON.parseObject(SMSVerification.sendPost("http://api.mysubmail.com/message/send",paramItem));
            if(item.get("status").toString().equals("success")){
                redisUtil.set(params.get("phone"),String.valueOf(num),36000);
                return Result.success(CodeEnum.SUCCESS);

            }else{

                return Result.fail(CodeEnum.REGISTER_FAILED);
            }
        }else{
            return Result.fail(CodeEnum.REGISTER_FAILED);
        }

    }


    /**
     * 登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public Result login(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, String> params) {
        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String phone = params.get("phone");
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute(Constants.SESSION_KEY);
        }
        JWTUtil.encode(phone,0);
        return Result.success();
    }



    /**
     * 修改密码
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/update_password")
    public Result update_password(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {

        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }

        String token = request.getHeader("token");
        String phone = params.get("phone");
        String oldPassword = params.get("oldPassword");
        String password = params.get("password");
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(password)) {
            throw new ValidationException();
        }
        if(phone.equals(JWTUtil.decode(token)))
        {
            Object object = userService.login(phone,oldPassword);
            if (object instanceof User) {
                User user=new User();
                user.setPhone(phone);
                user.setPassword(Md5Util.getMD5(password));
                ICode str =userService.updatePass(user);
                if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
                    return Result.success();
                } else {
                    return Result.fail(str);
                }
            } else {
                return Result.fail((CodeEnum) object);
            }
        }else{
            return Result.fail(CodeEnum.ERROR_CODE);
        }


    }

    /**
     * 修改用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/update_user_info")
    public Result update_user_info(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {

        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String token = request.getHeader("token");
        String phone = params.get("phone");
        String sex = params.get("sex");
        String userName = params.get("userName");
        if (StringUtils.isEmpty(token) ||StringUtils.isEmpty(phone) || StringUtils.isEmpty(sex) || StringUtils.isEmpty(userName)) {
            throw new ValidationException();
        }
        if(phone.equals(JWTUtil.decode(token)))
        {

            ICode str = userService.updateByPhone(phone,sex,userName);
            if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
                return Result.success();
            } else {
                return Result.fail(str);
            }
        }else{
            return Result.fail(CodeEnum.ERROR_CODE);
        }

    }
}
