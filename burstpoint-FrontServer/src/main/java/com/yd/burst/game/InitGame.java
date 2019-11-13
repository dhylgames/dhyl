package com.yd.burst.game;

import com.yd.burst.cache.CacheKey;
import com.yd.burst.cache.RedisPool;
import com.yd.burst.dao.GroupRoomMapper;
import com.yd.burst.model.GroupRoom;
import com.yd.burst.model.RoomInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InitGame {

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

    }
}
