package com.yd.burst.game;

import com.yd.burst.cache.CacheKey;
import com.yd.burst.cache.RedisPool;
import com.yd.burst.dao.GroupRoomMapper;
import com.yd.burst.model.GroupRoom;
import com.yd.burst.model.Player;
import com.yd.burst.model.RoomInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: kelly
 * @Date: 2019/11/13 09:50
 * @Description:
 */
@Component
public class InitGame implements ApplicationListener<ContextRefreshedEvent> {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static final int DEFAULT_PERSON_NUM = 6;

    @Autowired
    private RedisPool redisPool;

    @Resource
    private GroupRoomMapper groupRoomMapper;
    //系统初始化时，初始化有些的群房间信息
    public void setGroupRoomInfo(){
        //第一步，先查询数据库
       List<GroupRoom> roomList = groupRoomMapper.load();
        //第二步，查出来的数据存入redis
        for(GroupRoom groupRoom:roomList){
            String key = CacheKey.GROUP_KEY + groupRoom.getId();
            //先
            List<RoomInfo> roomInfos = new ArrayList<>();
            for(int i=1;i<=this.DEFAULT_PERSON_NUM;i++){
                RoomInfo roomInfo = new RoomInfo();
                roomInfo.setIndex(i);
                roomInfo.setPersonNum(0);
                roomInfo.setLevel(1);
                roomInfos.add(roomInfo);
            }
            redisPool.setData4Object2Redis(key,roomInfos);
        }
        List<Player> players = new ArrayList<>();
        Player player = new Player();
        player.setUserId(1);
        player.setReadyState(0);
        players.add(player);
        String key2 = CacheKey.GROUP_ROOM_KEY+11;
        redisPool.setData4Object2Redis(key2,players);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            logger.info("初始化开始。。。");
            setGroupRoomInfo();
        }
    }
}
