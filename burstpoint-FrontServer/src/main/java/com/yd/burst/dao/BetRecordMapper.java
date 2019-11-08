package com.yd.burst.dao;

import com.yd.burst.model.BetRecord;
import com.yd.burst.model.dto.GameAndPlayerDo;
import com.yd.burst.model.dto.GamePlayer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BetRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BetRecord record);

    Double selectTotalProfitByIssueNo(Long issueNo);

    BetRecord selectByUniqueKey(BetRecord record);

    List<BetRecord> selectByPlayerId(@Param("playerId") Integer playerId);

    int updateByUniqueKey(BetRecord record);

    int updateByPlayerList(List<GamePlayer> record);

    int insertNextBetPlayer(List<GamePlayer> record);

    List<GameAndPlayerDo> selectByIssueNo(@Param("issueNo") Long issueNo);
}
