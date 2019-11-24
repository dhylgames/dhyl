package com.yd.burst.dao;


import com.yd.burst.model.GameResult;

import java.util.List;

public interface GameResultMapper {

    public int saveGameResultBatch(List<GameResult> gameResults);

}
