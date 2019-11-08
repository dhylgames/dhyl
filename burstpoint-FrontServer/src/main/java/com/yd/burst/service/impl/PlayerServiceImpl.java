package com.yd.burst.service.impl;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.cache.RedisPool;
import com.yd.burst.dao.PlayerMapper;
import com.yd.burst.dao.WalletMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.enums.RoleEnum;
import com.yd.burst.enums.UserStatusEnum;
import com.yd.burst.model.Player;
import com.yd.burst.model.Wallet;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.PlayerService;
import com.yd.burst.util.DateUtil;
import com.yd.burst.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:51
 **/
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private CacheBase cacheBase;

    @Autowired
    private WalletMapper walletMapper;



    @Override
    public ICode register(Player player) {
        ICode code;
        Player playerTemp = playerMapper.selectPlayer(player.getPlayerName());
        if (playerTemp != null) {
            return CodeEnum.EXIST_PLAYER;
        }
        player.setPassword(Md5Util.getMD5(player.getPassword()));
//        player.setSafePwd(Md5Util.getMD5(player.getSafePwd()));
        player.setCreateTime(DateUtil.dateToStr(new Date()));
        player.setUserStatus(UserStatusEnum.NORMAL.getCode());
        // 配置管理中读取，是否开启机器人注册
        player.setPlayerType(RoleEnum.PLAY.getCode());
        int count = playerMapper.insert(player);

   /*     if (RoleEnum.PLAY.getCode().equals(player.getPlayerType())){
            // 初始化钱包
            Wallet wallet = new Wallet();
            wallet.setPlayerId(player.getId());
            wallet.setCreateTime(DateUtil.dateToStr(new Date()));
            wallet.setCreateBy(player.getId().toString());
            walletMapper.insert(wallet);
        }*/
        if (count > 0) {
            cacheBase.refreshPlayer(player.getPlayerName());
            code = CodeEnum.SUCCESS;
        } else {
            code = CodeEnum.REGISTER_FAILED;
        }
        return code;
    }

    @Override
    public Player findByName(String playerName) {
        return playerMapper.selectPlayer(playerName);
    }

    @Override
    public Object login(String playerName, String password) {
        PlayerWallet player = playerMapper.selectByPlayerName(playerName);
        if (player != null) {
            if (RoleEnum.NPC.getCode().equals(player.getPlayerType())) {
                return CodeEnum.LOGIN_FAILED;
            }
            if (Md5Util.getMD5(password).equals(player.getPassword())) {
                player.setLastLoginTime(DateUtil.dateToStr(new Date()));
                boolean flag = playerMapper.updateLastLoginTime(player);
                if (flag) {
                    return player;
                }
            } else {
                return CodeEnum.ERROR_PASSWORD;
            }
        } else {
            return CodeEnum.NOT_EXIST_PLAYER;
        }
        return CodeEnum.LOGIN_FAILED;
    }
}

