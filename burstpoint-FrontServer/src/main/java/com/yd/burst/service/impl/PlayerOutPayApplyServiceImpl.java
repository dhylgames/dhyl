package com.yd.burst.service.impl;

import com.yd.burst.dao.PlayerMapper;
import com.yd.burst.dao.PlayerOutPayApplyMapper;
import com.yd.burst.enums.CodeEnum;
import com.yd.burst.enums.ICode;
import com.yd.burst.model.PlayerOutPayApply;
import com.yd.burst.model.dto.PlayerWallet;
import com.yd.burst.service.PlayerOutPayApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-08-19 15:11
 **/
@Service
@Transactional
public class PlayerOutPayApplyServiceImpl implements PlayerOutPayApplyService {

    @Autowired
    private PlayerOutPayApplyMapper applyMapper;

    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public ICode payOutApply(PlayerOutPayApply apply) {

        PlayerWallet playerWallet = playerMapper.selectByPlayerId(Integer.valueOf(apply.getUserid()));
        if (playerWallet.getAvailMoney() > apply.getMoney() && playerWallet.getTotalMoney() > apply.getMoney()) {
            apply.setStatus(0);
            int i = applyMapper.insert(apply);
            if (i > 0) {
                return CodeEnum.WITHDRAY_SUCCESS;
            }
            return CodeEnum.WITHDRAY_ERROR;
        }
        return CodeEnum.WITHDRAY_FAILED;
    }
}
