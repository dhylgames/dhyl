package com.yd.burst.dao;

import com.yd.burst.model.PlayerOutPayApply;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PlayerOutPayApplyMapper {

    int insert(PlayerOutPayApply playerOutPayApply);
}
