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
import java.util.Map;
import java.util.stream.Collectors;

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
       //按群号分组
       Map<String, List<GroupRoom>> map = roomList.stream().collect(Collectors.groupingBy(GroupRoom::getGroupCode));
        for(String groupCode:map.keySet()){
            List<GroupRoom> list = map.get(groupCode);
            //第二步，查出来的数据存入redis
            String key = CacheKey.GROUP_KEY + groupCode;
            redisPool.setData4Object2Redis(key,list);

           /*for(GroupRoom groupRoom:list){ //房间
               List<Player> players = new ArrayList<>();
               String key2 = CacheKey.GROUP_ROOM_KEY+11;
               redisPool.setData4Object2Redis(key2,players);
            }*/
        }

        /*List<Player> players = new ArrayList<>();
        Player player = new Player();
        player.setUserId(1);
        player.setReadyState(0);
        players.add(player);
        String key2 = CacheKey.GROUP_ROOM_KEY+11;
        redisPool.setData4Object2Redis(key2,players);*/
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            logger.info("初始化开始。。。");
            setGroupRoomInfo();
        }
    }
}
