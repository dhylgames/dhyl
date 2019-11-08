package com.yd.burst.dao;

import com.yd.burst.model.Player;
import com.yd.burst.model.dto.PlayerWallet;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface PlayerMapper {

    List<Player> load();

    int insert(Player player);

    Player selectPlayer(String playerName);

    PlayerWallet selectByPlayerName(String playerName);

    PlayerWallet selectByPlayerId(Integer playerId);

    boolean updateLastLoginTime(PlayerWallet player);

    List<Player> loadNpcPlayer();
}
