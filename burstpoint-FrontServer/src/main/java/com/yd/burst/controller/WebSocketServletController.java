package com.yd.burst.controller;

import com.alibaba.fastjson.JSONObject;
import com.yd.burst.util.ConfigUtil;
import com.yd.burst.util.Constants;
import com.yd.burst.util.GameContext;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@ServerEndpoint(value = "/websocket")
public class WebSocketServletController extends HttpServlet {
    //用来存放每个客户端对应的MyWebSocket对象。
    private static ThreadLocal<Session> sessionLocal = new ThreadLocal<>();

    //开启连接
    @OnOpen
    public void onOpen(Session session) throws IOException {
        try {
            sessionLocal.set(session);
            // 初次连接传已产生的点位
            if (Constants.GAMING == GameContext.GAME_CONTEXT.getStatus()) {
                List<BigDecimal> list = GameContext.GAME_CONTEXT.getCurrentPointList();
                StringBuffer sb = new StringBuffer("INIT");

                sb.append(GameContext.GAME_CONTEXT.getIncreaseRatio());
                sb.append(',');
                sb.append(GameContext.GAME_CONTEXT.getAcceleration());
                sb.append(',');
                sb.append(ConfigUtil.getFrontRefreshInterval());
                sb.append(',');

                for (BigDecimal item : list) {
                    sb.append(item);
                    sb.append(',');
                }
                this.onMessage(sb.toString());
            }

            Thread.sleep(ConfigUtil.getFrontSyncInterval());
            boolean isOK = true;
            while (isOK) {
                this.show();
                Thread.sleep(ConfigUtil.getFrontSyncInterval());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //关闭连接
    @OnClose
    public void onClose() {
        sessionLocal.remove();
    }

    //给服务器发送消息告知数据库发生变化
    @OnMessage
    public void onMessage(String count) {
        try {
            sendMessage(count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //出错的操作
    @OnError
    public void onError(Throwable error) {
        System.out.println(error);
        error.printStackTrace();
    }

    public void show() {
        this.onMessage("history" + JSONObject.toJSONString(GameContext.GAME_CONTEXT.getStayedList()));
        if (Constants.WAIT_START == GameContext.GAME_CONTEXT.getStatus()) {
            this.onMessage(String.valueOf("WAIT_START" + new BigDecimal(GameContext.GAME_CONTEXT.getTime()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()));
        }

        if (Constants.GAMING == GameContext.GAME_CONTEXT.getStatus()) {
            StringBuffer sb = new StringBuffer("GAMING");

            sb.append(GameContext.GAME_CONTEXT.getIncreaseRatio());
            sb.append(',');
            sb.append(GameContext.GAME_CONTEXT.getAcceleration());
            sb.append(',');
            sb.append(ConfigUtil.getFrontRefreshInterval());
            sb.append(',');
            sb.append(String.valueOf(GameContext.GAME_CONTEXT.getCurrentPoint().setScale(4, BigDecimal.ROUND_HALF_UP)));
            this.onMessage(sb.toString());
        }

        if (Constants.GAMEOVER == GameContext.GAME_CONTEXT.getStatus()) {
            this.onMessage(String.valueOf("GAMEOVER" + GameContext.GAME_CONTEXT.getCurrentPoint().setScale(4, BigDecimal.ROUND_HALF_UP)));
        }

    }

    /**
     * @throws IOException
     */
    public void sendMessage(String count) throws IOException {
        //群发消息
        sessionLocal.get().getBasicRemote().sendText(count);
    }
}
