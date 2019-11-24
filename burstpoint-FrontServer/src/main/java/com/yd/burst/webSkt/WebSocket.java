package com.yd.burst.webSkt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yd.burst.cache.CacheKey;
import com.yd.burst.cache.RedisPool;
import com.yd.burst.controller.GroupInfoController;
import com.yd.burst.enums.GameOperationEnum;
import com.yd.burst.game.CreatPoker;
import com.yd.burst.game.NnCompare;
import com.yd.burst.model.BeanForm;
import com.yd.burst.model.GroupRoom;
import com.yd.burst.model.Player;
import com.yd.burst.model.User;
import com.yd.burst.service.GameResultService;
import com.yd.burst.service.UserService;
import org.apache.commons.collections.ArrayStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * userId 用户id
 * groupId 群号
 * roomId  房间id
 */
@ServerEndpoint(value = "/websocket/{userId}/{groupCode}/{roomCode}",configurator=MyEndpointConfigure.class)
@Component
public class WebSocket implements Serializable {

    @Autowired
    private RedisPool redisPool;

    @Autowired
    private GameResultService gameResultService;

    @Autowired
    private UserService userService;

    private static Logger logger = LogManager.getLogger(GroupInfoController.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentMap<String, CopyOnWriteArraySet<WebSocket>> roomWebSockets = new ConcurrentHashMap<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据

    private static ConcurrentMap<String,Session> sessionSet;

    private static final String SYMBOL = "_";


    /**
     * 连接建立成功调用的方法
     **/
    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId, @PathParam("groupCode")String groupCode, @PathParam("roomCode")String roomCode) {
        //根据当前用户来查出是那个群，那个房间，然后根据群和房间存放websocket
        //根据当前群号房间好拼成key,存入redis,有利于游戏是，根据房间的所有在玩的玩家统一消息发送
        String websocketKey = getKey(CacheKey.WEBSOCKET_KEY,groupCode,roomCode);
        CopyOnWriteArraySet<WebSocket> roomWebSocket = roomWebSockets.get(websocketKey);
        if(null == roomWebSocket){
            roomWebSocket = new CopyOnWriteArraySet<>();
        }
        if(roomWebSocket.size()>0){
            for(WebSocket sessionWebSocket:roomWebSocket){
                ConcurrentMap<String,Session> sessionSet = sessionWebSocket.getSessionSet();
                if(sessionSet==null){
                    sessionSet = new ConcurrentHashMap<>();
                }
                sessionSet.put(userId,session);
                sessionWebSocket.setSessionSet(sessionSet);
            }
        }else{
            ConcurrentMap<String,Session> sessionSet = new ConcurrentHashMap<>();
            sessionSet.put(userId,session);
            setSessionSet(sessionSet);
        }
        setRoomPlayer(userId,groupCode,roomCode);
        roomWebSocket.add(this);     //分组的，加入set中
        webSocketSet.add(this); //全局的
        roomWebSockets.put(websocketKey,roomWebSocket);
      //  addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数:" + this.onlineCount);
        try {
            session.getBasicRemote().sendText("已连接");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addUserToRoom(String userId,String groupCode, String roomCode){
        String key = CacheKey.GROUP_KEY + groupCode;



    }


    public void setRoomPlayer(String userId, String groupCode, String roomCode) {
        String key = getKey(CacheKey.GROUP_ROOM_KEY, groupCode, roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
        if (null == players) players = new ArrayList<>();
        boolean isExist = false;
        for (Player player : players) {
            if (Integer.valueOf(userId) == player.getUserId()) {
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            Player player = new Player();
            player.setUserId(Integer.parseInt(userId));
            players.add(player);
        }
        redisPool.setData4Object2Redis(key, players);
        setUserToRoom(players, groupCode, roomCode);

    }

    public void setUserToRoom(List<Player> players,String groupCode, String roomCode){
        List<User> userList = new ArrayList<>();
        for(Player player:players){
            int userId = player.getUserId();
            User user = userService.selectUserById(userId);
            user.setPassword("");
            userList.add(user);
        }
        //往群号添加结构
        List<GroupRoom> roomList = (List<GroupRoom>) redisPool.getData4Object2Redis(CacheKey.GROUP_KEY+groupCode);
        for (GroupRoom room : roomList) {
            if (room.getId() == Integer.parseInt(roomCode)) {
                room.setGroupUserList(userList);
            }
        }
        redisPool.setData4Object2Redis(groupCode,roomList);
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
        BeanForm beanForm= object.toJavaObject(BeanForm.class);
        String websocketKey = getKey(CacheKey.WEBSOCKET_KEY,beanForm.getGroupCode(),beanForm.getRoomCode());
        logger.info("来自客户端的消息2:{}" ,object);
        CopyOnWriteArraySet<WebSocket> roomWebSocket = roomWebSockets.get(websocketKey);
        //群发消息
        for (WebSocket item : roomWebSocket) {
            try {
                String msg =null;
                switch (beanForm.getPlayType()){
                    case "0":
                        msg= NNPlay(beanForm);
                        break;
                    case "1":
                        msg= FGFPlay(beanForm);
                        break;
                }
                sendMessage(item,msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String NNPlay(BeanForm beanForm){
        int opType = Integer.parseInt(beanForm.getOpType());
        GameOperationEnum gameOperationEnum = GameOperationEnum.getGameOperationEnum(opType);
        String msg =null;
        switch (gameOperationEnum){
            case CATTLE_READY://准备
                msg= cattleReadyOp(beanForm);
                // System.out.println(msg);
                break;
            case CATTLE_GRAB_BANKER: //抢庄
                msg= cattleGrabBanker(beanForm);
                //  System.out.println(msg);
                break;
            case CATTLE_DEAL://发牌
                msg = sendPoker(beanForm);
                //  System.out.println(msg);
                break;
            case CATTLE_SHOW://亮牌
                msg = cattleShowCard(beanForm);
                //  System.out.println(msg);
                break;
            case CATTLE_CALC_SCORE: //计算分
                msg= cattleCalcScore(beanForm);
                //  System.out.println(msg);
                break;
            case GAME_AGAIN:
                msg = clearGameData(beanForm);
        }
       return msg;
    }
    public String clearGameData(BeanForm beanForm){
        String groupCode = beanForm.getGroupCode();
        String roomCode = beanForm.getRoomCode();
        //在数据库中查出玩家
        String key = getKey(CacheKey.GROUP_ROOM_KEY,groupCode,roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
        List<Player> newPlayers = new ArrayList<>();
        for(Player player:players){
            player.setPocket(null);
            player.setScore(0);
            player.setIsWinAll(0);
            player.setPointOfBull(0);
            player.setBanker(false);
            newPlayers.add(player);
        }
        redisPool.setData4Object2Redis(key,newPlayers);
        return "200";
    }
    //计算得分
    private String cattleCalcScore(BeanForm beanForm) {
        String groupCode = beanForm.getGroupCode();
        String roomCode = beanForm.getRoomCode();
        //在数据库中查出玩家
        String key = getKey(CacheKey.GROUP_ROOM_KEY,groupCode,roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
        NnCompare compare = new NnCompare();
        players = compare.compare(players);
        redisPool.setData4Object2Redis(key, players);
        //战绩插入数据库 做异步操作
        gameResultService.saveGameResult(players,beanForm);
        return JSON.toJSONString(players);
    }

    //牛牛亮牌
    private String cattleShowCard(BeanForm beanForm) {
        String groupCode = beanForm.getGroupCode();
        String roomCode = beanForm.getRoomCode();
        //在数据库中查出这个用户
        String key = getKey(CacheKey.GROUP_ROOM_KEY,groupCode,roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
        //手相判断是否有牛
        for(int k = 0;k<players.size();k++){
            Player player = players.get(k);
            NnCompare compare = new NnCompare();
            if(compare.isBull(player)){
             //有牛的话，要计算牛几
             players.get(k).setBull(true);
             int nNum = compare.pointOfBull(player);
             players.get(k).setPointOfBull(nNum);
            }else{
                players.get(k).setBull(false);
            }
        }
        return JSON.toJSONString(players);
    }

    //牛牛发牌
    private String sendPoker(BeanForm beanForm) {
        String groupCode = beanForm.getGroupCode();
        String roomCode = beanForm.getRoomCode();
        //在数据库中查出这个用户
        String key = getKey(CacheKey.GROUP_ROOM_KEY,groupCode,roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
        //创建牌
        players = new CreatPoker().CreatPoker(players);
        //把牌写入redis
        redisPool.setData4Object2Redis(key, players);
        return JSON.toJSONString(players);
    }

    //牛牛抢庄
    private String cattleGrabBanker(BeanForm beanForm) {
        String groupCode = beanForm.getGroupCode();
        String roomCode = beanForm.getRoomCode();
        //在数据库中查出这个用户
        String key = getKey(CacheKey.GROUP_ROOM_KEY,groupCode,roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
        //从玩家中随机一个做庄家
        int banker = 0;
        if(players.size()>0){
            banker = new Random().nextInt(players.size());
        }
        players.get(banker).setBanker(true);
        redisPool.setData4Object2Redis(key, players);
        return  JSON.toJSONString(players);
    }

    //牛牛准备的逻辑
    private String cattleReadyOp(BeanForm beanForm) {
        //把这个用户根据群号，房间号插入redis中，
        //进入房间的时候要根据群号，房间号增加用户信息
        String userId = beanForm.getUserId();
        String groupCode = beanForm.getGroupCode();
        String roomCode = beanForm.getRoomCode();
       //在数据库中查出这个用户
        String key = getKey(CacheKey.GROUP_ROOM_KEY,groupCode,roomCode);
        List<Player> players = (List<Player>) redisPool.getData4Object2Redis(key);
         for(Player player : players){
             if(player.getUserId() == Integer.parseInt(userId)){
                 player.setReadyState(1);
             }
         }
        redisPool.setData4Object2Redis(key, players);
        return  JSON.toJSONString(players);
    }

    private String FGFPlay(BeanForm beanForm){
        int opType = Integer.parseInt(beanForm.getOpType());
        GameOperationEnum gameOperationEnum = GameOperationEnum.getGameOperationEnum(opType);
        String msg =null;
        switch (gameOperationEnum){
            case FGF_READY:
                msg=showCard(JSON.toJSONString(beanForm));
                // System.out.println(msg);
                break;
            case FGF_DEAL:
                msg=startGame(JSON.toJSONString(beanForm)).toString();
                //  System.out.println(msg);
                break;
            case FGF_FOLLOW_BET:
                msg=enteringRoom(JSON.toJSONString(beanForm)).toString();
                //  System.out.println(msg);
                break;
            case FGF_SEE_CARD:
                msg=outRoom(JSON.toJSONString(beanForm)).toString();
                //  System.out.println(msg);
                break;
            case FGF_COMPARE_CARD:
                msg=readyStatus(JSON.toJSONString(beanForm)).toString();
                //  System.out.println(msg);
                break;
            case FGF_DISCARD:
                msg=integralCalc(JSON.toJSONString(beanForm)).toString();
                //  System.out.println(msg);
                break;
            case FGF_CALC_SCORE:
                msg=ratioCard(JSON.toJSONString(beanForm)).toString();
                //  System.out.println(msg);
                break;
        }
        return null;
    }


    //发生错误时调用
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }


     public void sendMessage(WebSocket webSocket,String message) throws IOException {
         ConcurrentMap<String,Session> sessionSet = webSocket.getSessionSet();
         for(String key:sessionSet.keySet()){
             Session session = sessionSet.get(key);
             session.getBasicRemote().sendText(message);
         }
     //this.session.getAsyncRemote().sendText(message);
     }


     /**
      * 群发自定义消息
      * */
    public static void sendInfo(String message) throws IOException {
        /*for (WebSocket item : webSocketSet) {
            try {.sendMessage(item,message);
            } catch (IOException e) {
                continue;
            }
        }*/
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

    private String getKey(String key,String param1,String param2){
        return key + param1 + SYMBOL + param2;
    }


    /**
     *  两个玩家比牌
     * @param obj Json 对象 {"roomId":123456,"user":123456,"method":"ratioCard"}
     * @return
     */
    public String ratioCard(Object obj){
        return  null;
    }

    /**
     *  当局游戏结束
     * @param obj Json 对象 {"roomId":123456,"user":123456,"method":"endGame"}
     * @return
     */
    public String endGame(Object obj){
        return  null;
    }

    public static ConcurrentMap<String, Session> getSessionSet() {
        return sessionSet;
    }

    public static void setSessionSet(ConcurrentMap<String, Session> sessionSet) {
        WebSocket.sessionSet = sessionSet;
    }
}
