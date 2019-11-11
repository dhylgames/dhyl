package com.yd.burst.webSkt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/websocket/{opNum}")
@Component
public class WebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private String opNum;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(@PathParam("opNum") String opNum, Session session) {
        this.opNum = opNum;
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        System.out.println("有新连接加入！opNum为" + this.opNum);
        try {
            sendMessage("测链接");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        JSONObject object = JSON.parseObject(message);
        //群发消息
        for (WebSocket item : webSocketSet) {
            try {
                String msg =null;
                switch (object.get("method").toString()){
                    case "showCard":
                      msg=showCard(JSON.toJSONString(object));
                     // System.out.println(msg);
                     break;
                    case "startGame":
                        msg=startGame(JSON.toJSONString(object)).toString();
                      //  System.out.println(msg);
                     break;
                    case "enterRoom":
                        msg=enteringRoom(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                     break;
                    case "outRoom":
                        msg=outRoom(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                        break;
                    case "readyStatus":
                        msg=readyStatus(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                     break;
                    case "integralCalc":
                        msg=integralCalc(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                    break;
                    case "ratioCard":
                        msg=ratioCard(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                     break;
                    case "openCard":
                        //全部开牌
                        //msg=readyStatus(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                     break;
                    case "endGame":
                        //游戏结束
                        msg=endGame(JSON.toJSONString(object)).toString();
                        //  System.out.println(msg);
                     break;
                }
                item.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        //发生错误时调用
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }


     public void sendMessage(String message) throws IOException {
     this.session.getBasicRemote().sendText(message);
     //this.session.getAsyncRemote().sendText(message);
     }


     /**
      * 群发自定义消息
      * */
    public static void sendInfo(String message) throws IOException {
        for (WebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocket.onlineCount--;
    }


    /**
     * 开始游戏
     * @param obj 对象 {"roomId":123456,"method":"startGame","bureau":1}
     * @return 结果
     */
    private String startGame(Object obj){
        JSONObject json = JSON.parseObject(obj.toString());
       return  null;
    }



    /**
     * 亮牌
     * @param obj Json 对象 {"roomId":123456,"userId":10001,"method":"showCard"}
     * @return 结果
     */
    private String showCard(Object obj){
        return  null;
    }

    /**
     * 用户进入房间
     * @param obj Json 对象 {"roomId":123456,"userId":10001,"method":"enteringRoom"}
     * @return 结果
     */
    private String enteringRoom (Object obj){
        return  null;
    }

    /**
     * 用户退出房间
     * @param obj Json 对象 {"roomId":123456,"userId":10001,"method":"outRoom"}
     * @return 结果
     */
    private String outRoom (Object obj){
        return  null;
    }

    /**
     * 用户准备与取消准备  0-未准备  1-已准备
     * @param obj Json 对象 {"roomId":123456,"userId":10001,"status":1,"method":"readyStatus"}
     * @return 结果 0-未准备  1-已准备  200-全部准备
     */
    private String readyStatus (Object obj){
        return  null;
    }


    /**
     *  计算积分
     * @param obj Json 对象 {"roomId":123456,"userId":123456,"integral":10,"method":"integralCalc "}
     * @return
     */
    public String integralCalc(Object obj){
        return  null;
    }


    /**
     *  两个玩家比牌
     * @param obj Json 对象 {"roomId":123456,"user":123456,"method":"ratioCard"}
     * @return
     */
    public String ratioCard(Object obj){
        return  null;
    }


    private Integer number(String str){
        Integer num = 0;
        switch (str) {
            case "2":
                num = 2;
                break;
            case "3":
                num = 3;
                break;
            case "4":
                num = 4;
                break;
            case "5":
                num = 5;
                break;
            case "6":
                num = 6;
                break;
            case "7":
                num = 7;
                break;
            case "8":
                num = 8;
                break;
            case "9":
                num = 9;
                break;
            case "10":
                num = 10;
                break;
            case "J":
                num = 11;
                break;
            case "Q":
                num = 12;
                break;
            case "K":
                num = 13;
                break;
            case "A":
                num = 14;
                break;
        }
        return  num;
    }

    /**
     *  当局游戏结束
     * @param obj Json 对象 {"roomId":123456,"user":123456,"method":"endGame"}
     * @return
     */
    public String endGame(Object obj){
        return  null;
    }
}
