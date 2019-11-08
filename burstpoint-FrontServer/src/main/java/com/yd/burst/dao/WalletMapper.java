package com.yd.burst.dao;

import com.yd.burst.model.Wallet;
import com.yd.burst.model.dto.GamePlayer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WalletMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPlayerId(@Param("playerId") Integer playerId);

    int updateByPrimaryKeySelective(Wallet record);

    int updateAmount(Wallet wallet);

    int updateByList(List<GamePlayer> list);
}
