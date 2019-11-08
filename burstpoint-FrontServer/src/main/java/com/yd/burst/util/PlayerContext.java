package com.yd.burst.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yd.burst.model.Player;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-14 19:21
 **/
public class PlayerContext {

    private Player player;

    private static PlayerContext context = new PlayerContext();

    private PlayerContext() {
    }

    public static PlayerContext getInstance(){
        return context;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
