package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.common.StaticRource;
import com.yd.burst.dao.GameResultMapper;
import com.yd.burst.dao.UserMapper;
import com.yd.burst.model.BeanForm;
import com.yd.burst.model.GameResult;
import com.yd.burst.model.Player;
import com.yd.burst.service.GameResultService;
import com.yd.burst.service.GroupRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:51
 **/
@Service
@Transactional
public class GameResultServiceImpl implements GameResultService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GameResultMapper gameResultMapper;

    @Autowired
    private CacheBase cacheBase;


    @Override
    public void saveGameResult(List<Player> players, BeanForm beanForm){
        List<GameResult> gameResults = new ArrayList<>();
        for(Player player:players){
            GameResult gameResult = new GameResult();
            gameResult.setGroupCode(beanForm.getGroupCode());
            gameResult.setGroupRoomId(beanForm.getRoomCode());
            gameResult.setGroupUserId(beanForm.getUserId());
            gameResult.setResult(String.valueOf(player.getScore()));
            gameResult.setIssue(beanForm.getIssue());
            SimpleDateFormat sdf = new SimpleDateFormat(StaticRource.DATEFORMATE);
            gameResult.setCreateTime(sdf.format(new Date()));
            gameResult.setUpdateTime(sdf.format(new Date()));
            gameResults.add(gameResult);
        }
        //批量插入战绩表
       int k = gameResultMapper.saveGameResultBatch(gameResults);
       if(k>0){
           logger.info("战绩插入成功！");
       }


    }

}

