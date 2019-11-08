package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.GameMapper;
import com.yd.burst.enums.GameStatusEnum;
import com.yd.burst.model.Game;
import com.yd.burst.model.dto.GameDto;
import com.yd.burst.model.dto.GamePlayer;
import com.yd.burst.service.GameService;
import com.yd.burst.util.ConfigUtil;
import com.yd.burst.util.Constants;
import com.yd.burst.util.DateUtil;
import com.yd.burst.util.GameContext;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 20:28
 **/
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private static Logger logger = LogManager.getLogger(GameServiceImpl.class);

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private CacheBase cacheBase;

    @Override
    public List<Game> getAllBurstPoints() {
        return gameMapper.selectAllBurstPoints();
    }

    @Override
    public Game createNewGame() {
        Game game = gameMapper.selectLastGame();
        if (game == null) {
            game = new Game();
            game = createGame(game, 1L);
            gameMapper.insert(game);
        } else if (game.getEndTime() != null) {
            game = createGame(game, game.getIssueNo() + 1L);
            gameMapper.insert(game);
        }
        BigDecimal decimal = game.getBurstPoint() != null ? new BigDecimal(game.getBurstPoint().toString()) : BigDecimal.ZERO;
        GameContext.GAME_CONTEXT.initGame(Integer.valueOf(game.getGameMode()), game.getIssueNo(), decimal);
        cacheBase.refreshGame(game.getIssueNo());
        return game;
    }

    private Game createGame(Game game, Long issueNo) {
        game.setIssueNo(issueNo);
        game.setCreateTime(DateUtil.dateToStr(new Date()));
        int gameMode = ConfigUtil.getGameMode();
        BigDecimal decimal;
        if (Constants.MODE_KILL == gameMode) {
            float point = ConfigUtil.getBurstPoint();
            decimal = new BigDecimal(String.valueOf(point)).setScale(4, BigDecimal.ROUND_HALF_UP);
            game.setBurstPoint(NumberUtils.toDouble(decimal));
        } else if (Constants.MODE_PROBABILITY == gameMode) {
            decimal = ConfigUtil.randomBurstPoint().setScale(4, BigDecimal.ROUND_HALF_UP);
            game.setBurstPoint(NumberUtils.toDouble(decimal));
        } else {
            game.setBurstPoint(null);
        }
        game.setTotalBonusPool(NumberUtils.toDouble(GameContext.GAME_CONTEXT.getTotalBonusPool()));
        game.setGameMode(String.valueOf(gameMode));
        game.setGameStatus(GameStatusEnum.NORMAL.getCode());
        game.setEndTime(null);
        game.setCreateBy(issueNo == 1L ? "Admin" : "");
        return game;
    }

    @Override
    public boolean checkBurstPoint() {
        return GameContext.GAME_CONTEXT.checkBurst();
    }

    @Override
    public void calcIncreasePoint() {
        GameContext.GAME_CONTEXT.increase();
    }

    @Override
    public void dealAfterBurst(Game game) {
        gameMapper.updateByIssueNo(game);
    }

    @Override
    public Map<String, Object> getLotteryHistory(String multiple) {
        Map<String, Object> returnMap = new HashMap<>(16);
        List<GameDto> list = gameMapper.selectHistory();
        Map<String, Object> maxMap = new HashMap<>(8);
        Map<String, Object> minMap = new HashMap<>(8);
        if (multiple == null || multiple == "" || multiple.equals("1") || multiple.equals("0")) {
            Map<String, Object> tempMap = new HashMap<>(8);
            tempMap.put("multiple", "[1, ∞ )");
            tempMap.put("chance", 1);
            tempMap.put("count", list.size());
            tempMap.put("highestNum", list.size());
            tempMap.put("detail", list);
            returnMap.put("max", tempMap);
            return returnMap;
        } else {
            Iterator<GameDto> iterator = list.iterator();
            Double temp = Double.valueOf(multiple);
            int max = 0;
            int min = 0;
            int countMax = 1;
            int currentMax = 1;
            int countMin = 1;
            int currentMin = 1;
            while (iterator.hasNext()) {
                GameDto dto = iterator.next();
                if (dto.getBurstPoint() >= temp) {
                    maxMap.put("multiple", "[" + multiple + " ∞ )");
                    max += 1;
                    currentMax += 1;
                    currentMin = 1;
                } else {
                    minMap.put("multiple", "[1, " + multiple + ")");
                    min += 1;
                    currentMin += 1;
                    currentMax = 1;
                }
                if (currentMax > countMax) {
                 countMax = currentMax;
                }
                if (currentMin > countMin) {
                    countMin = currentMin;
                }
            }

            maxMap.put("chance", new BigDecimal((float)max/list.size()*100).setScale(2, BigDecimal.ROUND_HALF_UP));
            maxMap.put("count", max);
            maxMap.put("highestNum", countMax);

            minMap.put("chance", new BigDecimal((float)min/list.size()*100).setScale(2, BigDecimal.ROUND_HALF_UP));
            minMap.put("count", min);
            minMap.put("highestNum", countMin);
        }
        returnMap.put("max", maxMap);
        returnMap.put("mix", minMap);
        returnMap.put("detail", list);
        return returnMap;
    }
}
