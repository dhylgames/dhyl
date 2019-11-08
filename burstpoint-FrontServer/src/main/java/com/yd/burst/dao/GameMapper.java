package com.yd.burst.dao;

import com.yd.burst.model.Game;
import com.yd.burst.model.dto.GameDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GameMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByIssueNo(Long issueNo);

    int updateByIssueNo(Game game);

    int updateByPrimaryKey(Game record);

    List<Game> selectAllBurstPoints();

    Game selectLastGame();

    int updateTotalAmountByIssueNo(Game game);

    List<GameDto> selectHistory();
}
