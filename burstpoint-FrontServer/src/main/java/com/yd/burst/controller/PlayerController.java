package com.yd.burst.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.yd.burst.annotation.Redis;
import com.yd.burst.common.Result;
import com.yd.burst.dao.WalletMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.Player;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.PlayerService;
import com.yd.burst.util.Constants;
import com.yd.burst.util.WebUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Properties;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:59
 **/
@RestController
@RequestMapping("api/player")
public class PlayerController {

    private static Logger logger = LogManager.getLogger(PlayerController.class);
    private static DefaultKaptcha KAPTCHA_PRODUCER;

    @Autowired
    private PlayerService playerService;

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
        Player player = new Player();
        if (params != null && params.size() == 3) {
            player.setPlayerName(params.get("playerName"));
            player.setPassword(params.get("password"));
            player.setPhone(params.get("phone"));
        } else {
            return Result.fail(CodeEnum.REGISTER_FAILED);
        }
        ICode str = playerService.register(player);
        if (CodeEnum.SUCCESS.getCode().equals(str.getCode())) {
            return Result.success(player);
        } else {
            return Result.fail(str);
        }
    }

    /**
     * 玩家登录
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, String> params) {

        if (params == null) {
            return Result.fail(CodeEnum.VALIDATE_FAILED);
        }
        String playerName = params.get("playerName");
        String password = params.get("password");
        String code = params.get("code");
        if (StringUtils.isEmpty(playerName) || StringUtils.isEmpty(password) || StringUtils.isEmpty(code)) {
            throw new ValidationException();
        }
        Player player = new Player();
        player.setPlayerName(playerName);
        player.setPassword(password);

        HttpSession session = request.getSession();
        /*
         * 验证码校验
         */
//        if (!WebUtil.checkVerifyCode(request, code)) {
//            request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
////            SecurityUtils.getSubject().logout();
//            return Result.fail(CodeEnum.VERIFICATION_FAILED);
//        }
        Object object = playerService.login(player.getPlayerName(), player.getPassword());
        if (object instanceof PlayerWallet) {
            PlayerWallet playerWallet = (PlayerWallet) object;
            session.setAttribute(Constants.SESSION_KEY, playerWallet);
            return Result.success(playerWallet);
        } else {
            return Result.fail((CodeEnum) object);
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
     * 登出
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/logout")
    public Result login(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute(Constants.SESSION_KEY);
        }
        return Result.success();
    }

}
