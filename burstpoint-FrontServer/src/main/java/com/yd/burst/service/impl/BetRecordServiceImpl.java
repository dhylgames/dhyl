package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.dao.BetRecordMapper;
import com.yd.burst.dao.GameMapper;
import com.yd.burst.dao.PlayerMapper;
import com.yd.burst.dao.WalletMapper;
import com.yd.burst.enums.BetStatusEnum;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.EscapeStatusEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.BetRecord;
import com.yd.burst.model.Game;
import com.yd.burst.model.Wallet;
import com.yd.burst.model.dto.GameAndPlayerDo;
import com.yd.burst.model.dto.GamePlayer;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.BetRecordService;
import com.yd.burst.util.Constants;
import com.yd.burst.util.DateUtil;
import com.yd.burst.util.GameContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-30 20:23
 **/
@Service
@Transactional
public class BetRecordServiceImpl implements BetRecordService {

    @Autowired
    private BetRecordMapper betRecordMapper;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private CacheBase cacheBase;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private PlayerMapper playerMapper;

    /**
     * 下注之前需要验证传入的期号与创建游戏的最新期号是否一致
     *      如果一致，说明在6s的下注期限内，可以下注
     *      如果不一致，且注单期号减去实际存在的游戏期号等于1，说明是在游戏开始期间下注，此时创建一局新游戏并创建注单
     *      全局变量标识总下注额，并填去Game表中
     *
     *
     * @param record
     */
    @Override
    public ICode bet(BetRecord record, int status) {

        // 玩家钱包查询
        PlayerWallet playerWallet = playerMapper.selectByPlayerId(record.getPlayerId());
        if (playerWallet == null) {
            return CodeEnum.ERROR;
        }
        if (playerWallet.getAvailMoney() < record.getBetAmount()) {
            return CodeEnum.BALANCE_LESS;
        }
        //最后一局游戏判断
        Game game = gameMapper.selectLastGame();
        if (game == null) {
            return CodeEnum.BET_FAILED;
        }
        Long issueNo = game.getIssueNo();
        GamePlayer gamePlayer = new GamePlayer();
        gamePlayer.setPlayerName(playerWallet.getPlayerName());
        gamePlayer.setId(record.getPlayerId());
        gamePlayer.setCreateTime(DateUtil.dateToStr(new Date()));
        gamePlayer.setCapital(BigDecimal.valueOf(record.getBetAmount()));
        String betStstus = record.getMultiple() == null ? BetStatusEnum.MANUAL.getCode() : BetStatusEnum.AUTO.getCode();
        gamePlayer.setBetStatus(betStstus);
        if (betStstus.equals(BetStatusEnum.AUTO.getCode())) {
            gamePlayer.setMultiple(BigDecimal.valueOf(record.getMultiple()));
        }
        if (Constants.CURRENT_GAME == status) {
            gamePlayer.setIssueNo(issueNo);
            record.setIssueNo(issueNo);
            record.setCreateTime(DateUtil.dateToStr(new Date()));
            try{
                betRecordMapper.insert(record);
            } catch (Exception e) {
                return CodeEnum.CURRENT_BET_FAILED;
            }
        } else if (Constants.NEXT_GAME == status) {
            gamePlayer.setIssueNo(issueNo + 1L);
        } else {
            return CodeEnum.ERROR;
        }
        boolean flag = GameContext.GAME_CONTEXT.initPlayer(status, gamePlayer);
        if (!flag) {
            return CodeEnum.CURRENT_BET_FAILED;
        }
        return CodeEnum.SUCCESS;
    }



    @Override
    public ICode escape(BetRecord record) {
        List<GamePlayer> players = GameContext.GAME_CONTEXT.getEscapedList();
        if (players != null && players.size() > 0) {
            for (GamePlayer player: players) {
                if (record.getPlayerId().equals(player.getId())
                        && EscapeStatusEnum.SUCCESS.getCode().equals(player.getEscapeStatus())) {
                    return CodeEnum.AUTO_ESCAPED;
                }
            }
        }
        boolean isBurst = GameContext.GAME_CONTEXT.checkBurst();
        if (isBurst) {
            return CodeEnum.ESCAPE_FAILED;
        } else {
            GameContext.GAME_CONTEXT.dealNotBurstPoint(record.getPlayerId());
            return CodeEnum.SUCCESS;
        }
    }

    @Override
    public List<BetRecord> getAllBetRecords(Integer playerId) {
        return betRecordMapper.selectByPlayerId(playerId);
    }

    @Override
    public BetRecord getBetRecord(BetRecord record) {
        return null;
    }

    @Override
    public List<GameAndPlayerDo> getCurrentGameData(Long issueNo) {
        return betRecordMapper.selectByIssueNo(issueNo);
    }

    @Override
    public void dealAfterBurstPoint(List<GamePlayer> list) {
        betRecordMapper.updateByPlayerList(list);
        walletMapper.updateByList(list);
    }

    @Override
    public void dealNextGameBet(List<GamePlayer> list) {
        betRecordMapper.insertNextBetPlayer(list);
    }

}
