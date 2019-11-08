package com.yd.burst.service.impl;

import com.yd.burst.dao.PlayerMapper;
import com.yd.burst.dao.WalletMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.Player;
import com.yd.burst.model.Wallet;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yd.burst.util.DateUtil;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-09 14:10
 **/
@Service
@Transactional
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public void recharge(Integer playerId, Double amount) {
        Wallet wallet = new Wallet();
        PlayerWallet playerWallet = playerMapper.selectByPlayerId(playerId);
        wallet.setPlayerId(playerWallet.getId());
        wallet.setAvailMoney(playerWallet.getAvailMoney() + amount);
        wallet.setTotalMoney(playerWallet.getTotalMoney() + amount);
        wallet.setUpdateTime(DateUtil.dateToStr(new Date()));
        walletMapper.updateAmount(wallet);
    }

    @Override
    public Wallet withDraw(Integer playerId, Double amount) {
        PlayerWallet playerWallet = playerMapper.selectByPlayerId(playerId);
        Wallet wallet = null;
        if (playerWallet.getAvailMoney() > amount && playerWallet.getTotalMoney() > amount) {
            wallet = new Wallet();
            wallet.setPlayerId(playerWallet.getId());
            wallet.setAvailMoney(playerWallet.getAvailMoney() - amount);
            wallet.setTotalMoney(playerWallet.getTotalMoney() - amount);
            wallet.setUpdateTime(DateUtil.dateToStr(new Date()));
            walletMapper.updateAmount(wallet);
        }
        return wallet;
    }
}
