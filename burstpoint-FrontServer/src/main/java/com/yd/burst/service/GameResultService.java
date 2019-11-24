package com.yd.burst.service;

import com.yd.burst.model.BeanForm;
import com.yd.burst.model.Player;

import java.util.List;

/**
 * @Description:
 * @Author: Will
 * @Date: 2019-07-29 19:50
 **/
public interface GameResultService {
    public void saveGameResult(List<Player> players, BeanForm beanForm);
}
