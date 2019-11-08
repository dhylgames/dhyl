package com.yd.burst.cache;

import com.yd.burst.dao.*;
import com.yd.burst.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-05 15:48
 **/
@Service
public class CacheBase {


    @Autowired
    private BetRecordMapper betRecordMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private RedisPool redisPool;


    @Autowired
    private UserMapper userMapper;

    /**
     * 刷新历史记录
     * @param key
     */
    public void refreshPlayerBetHistory(Integer key) {
        List<BetRecord> list = betRecordMapper.selectByPlayerId(key);
        redisPool.setData4Object2Redis(CacheKey.BET_RECORD_KEY + String.valueOf(key), list);
    }

    public Object getPlayerBetHistory(String key) {
        return redisPool.getData4Object2Redis(CacheKey.BET_RECORD_KEY + String.valueOf(key));
    }

    /**
     * 刷新玩家
     * @param key
     */
    public void refreshPlayer(String key) {
        Player player = playerMapper.selectPlayer(key);
        redisPool.setData4Object2Redis(CacheKey.PLAYER_KEY + key, player);
    }


    /**
     * 刷新用户
     * @param key
     */
    public void refreshUser(String key) {
        User user = userMapper.selectPlayer(key);
        redisPool.setData4Object2Redis(CacheKey.USER_KEY + key, user);
    }

    public Object getPlayer(String key) {
        return redisPool.getData4Object2Redis(CacheKey.PLAYER_KEY + key);
    }

    /**
     * 刷新游戏
     * @param key
     */
    public void refreshGame(Long key) {
        Game player = gameMapper.selectByIssueNo(key);
        redisPool.setData4Object2Redis(CacheKey.GAME_KEY + key, player);
    }

    public Object getGame(String key) {
        return redisPool.getData4Object2Redis(CacheKey.GAME_KEY + key);
    }


    /**
     * 刷新单个配置
     * @param key
     */
    public void refreshConfig(String key) {
        Config config = configMapper.selectByKey(key);
        redisPool.setData4Object2Redis(CacheKey.CONFIG_KEY + key, config);
    }

    public Object getConfig(String key) {
        return redisPool.getData4Object2Redis(CacheKey.CONFIG_KEY + key);
    }

    /**
     * 刷新所有配置
     */
    public void refreshAllConfig() {
        List<Config> configList = configMapper.selectAllConfig();
        redisPool.setData4Object2Redis(CacheKey.CONFIG_KEY_ALL, configList);
    }

    public Object getAllConfig() {
        return redisPool.getData4Object2Redis(CacheKey.CONFIG_KEY_ALL);
    }


    public void refreshNpcPlayer() {
        List<Player> players = playerMapper.loadNpcPlayer();
        redisPool.setData4Object2Redis(CacheKey.NPC_PLAYER_KEY, players);
    }

    public Object getNpcPlayer() {
        return redisPool.getData4Object2Redis(CacheKey.NPC_PLAYER_KEY);
    }



}
