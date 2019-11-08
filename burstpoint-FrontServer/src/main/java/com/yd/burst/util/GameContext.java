package com.yd.burst.util;

import com.yd.burst.cache.CacheBase;
import com.yd.burst.enums.BetStatusEnum;
import com.yd.burst.enums.EscapeStatusEnum;
import com.yd.burst.model.Game;
import com.yd.burst.model.Player;
import com.yd.burst.model.dto.GamePlayer;
import com.yd.burst.service.BetRecordService;
import com.yd.burst.service.GameService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Will
 */
public class GameContext {
    private static BigDecimal HALF = new BigDecimal(0.5d);

    private static Logger logger = LogManager.getLogger(GameContext.class);
    public static GameContext GAME_CONTEXT = new GameContext();


    private static final String ESCAPE_SUCCESS = EscapeStatusEnum.SUCCESS.getCode();

    /**
     * 累减时间
     */
    private float time;

    /**
     * 期数
     */
    private Long issueNo;

    /**
     * 下一期期数
     */
    private String nextIssueNo;

    /**
     * 游戏模式
     */
    private int mode = 4;

    /**
     * 临时奖池，总下注额
     */
    private BigDecimal prize = BigDecimal.ZERO;

    /**
     * 当前点位
     */
    private BigDecimal currentPoint = new BigDecimal(ConfigUtil.getCurrentPoint());

    /**
     * 增长点
     */
    private BigDecimal increaseRatio = new BigDecimal(ConfigUtil.getIncreaseRatio());

    /**
     * 全局倍数
     */
    private float totalLimit = Float.valueOf(ConfigUtil.getTotalLimit());

    /**
     * 单局玩家盈利限额
     */
    private float personalLimit = Float.valueOf(ConfigUtil.getPersonalLimit());

    /**
     * 增长次数
     */
    private int count;

    /**
     * 通杀局爆点
     */
    private BigDecimal expectedPoint = BigDecimal.ZERO;

    /**
     * 普通
     */
    private BigDecimal expectedValue = BigDecimal.ZERO;

    /**
     * 放水局爆炸几率
     */
    private int burstProbability = Integer.valueOf(ConfigUtil.getBurstProbability());

    /**
     * 已逃跑
     */
    private List<GamePlayer> escapedList = new ArrayList<>();

    /**
     * 未逃跑
     */
    private List<GamePlayer> stayedList = new ArrayList<>();

    /**
     * 自动逃跑
     */
    private List<GamePlayer> autoEscapedList = new ArrayList<>();

    /**
     * 申请逃跑，提出申请的玩家仍然在StayedList里
     */
    private List<GamePlayer> applyList;

    /**
     * 通过宽容值判断出是否延迟爆炸
     */
    private boolean laterExplosion;

    /**
     * 自动逃跑限制
     */
    private BigDecimal autoEscapedLimit = new BigDecimal(ConfigUtil.getAutoEscapedLimit());

    /**
     * 总奖池
     */
    private BigDecimal totalBonusPool = new BigDecimal(ConfigUtil.getTotalAmount());

    /**
     * 下一局下注
     */
    private List<GamePlayer> nextGameBetList = new ArrayList<>();

    /**
     * 加速度
     */
    private BigDecimal acceleration = new BigDecimal(ConfigUtil.getAcceleration());

    /**
     * 记录所有的增长点
     */
    private List<BigDecimal> currentPointList = new ArrayList<>();

    /**
     * 逃跑临时列表，完成逃跑时动效
     */
    private Map<BigDecimal, List<GamePlayer>> escapeTempMap = new LinkedHashMap<>();

    /**
     * 逃跑临时列表
     */
    private List<GamePlayer> escapeTempList = new ArrayList<>();



    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public Long getIssueNo() {
        return issueNo;
    }

    public void setIssueNo(Long issueNo) {
        this.issueNo = issueNo;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public List<GamePlayer> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<GamePlayer> applyList) {
        this.applyList = applyList;
    }

    public boolean isLaterExplosion() {
        return laterExplosion;
    }

    public void setLaterExplosion(boolean laterExplosion) {
        this.laterExplosion = laterExplosion;
    }


    public String getNextIssueNo() {
        return nextIssueNo;
    }

    public void setNextIssueNo(String nextIssueNo) {
        this.nextIssueNo = nextIssueNo;
    }

    public BigDecimal getPrize() {
        return prize;
    }

    public void setPrize(BigDecimal prize) {
        this.prize = prize;
    }

    public BigDecimal getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(BigDecimal currentPoint) {
        this.currentPoint = currentPoint;
    }

    public BigDecimal getIncreaseRatio() {
        return increaseRatio;
    }

    public void setIncreaseRatio(BigDecimal increaseRatio) {
        this.increaseRatio = increaseRatio;
    }

    public float getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(float totalLimit) {
        this.totalLimit = totalLimit;
    }

    public float getPersonalLimit() {
        return personalLimit;
    }

    public void setPersonalLimit(float personalLimit) {
        this.personalLimit = personalLimit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getExpectedPoint() {
        return expectedPoint;
    }

    public void setExpectedPoint(BigDecimal expectedPoint) {
        this.expectedPoint = expectedPoint;
    }

    public BigDecimal getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(BigDecimal expectedValue) {
        this.expectedValue = expectedValue;
    }

    public int getBurstProbability() {
        return burstProbability;
    }

    public void setBurstProbability(int burstProbability) {
        this.burstProbability = burstProbability;
    }

    public List<GamePlayer> getEscapedList() {
        return escapedList;
    }

    public void setEscapedList(List<GamePlayer> escapedList) {
        this.escapedList = escapedList;
    }

    public List<GamePlayer> getStayedList() {
        return stayedList;
    }

    public void setStayedList(List<GamePlayer> stayedList) {
        this.stayedList = stayedList;
    }

    public List<GamePlayer> getAutoEscapedList() {
        return autoEscapedList;
    }

    public void setAutoEscapedList(List<GamePlayer> autoEscapedList) {
        this.autoEscapedList = autoEscapedList;
    }

    public BigDecimal getAutoEscapedLimit() {
        return autoEscapedLimit;
    }

    public void setAutoEscapedLimit(BigDecimal autoEscapedLimit) {
        this.autoEscapedLimit = autoEscapedLimit;
    }

    public BigDecimal getTotalBonusPool() {
        return totalBonusPool;
    }

    public void setTotalBonusPool(BigDecimal totalBonusPool) {
        this.totalBonusPool = totalBonusPool;
    }

    public List<GamePlayer> getNextGameBetList() {
        return nextGameBetList;
    }

    public void setNextGameBetList(List<GamePlayer> nextGameBetList) {
        this.nextGameBetList = nextGameBetList;
    }

    public BigDecimal getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(BigDecimal acceleration) {
        this.acceleration = acceleration;
    }

    public List<BigDecimal> getCurrentPointList() {
        return currentPointList;
    }

    public void setCurrentPointList(List<BigDecimal> currentPointList) {
        this.currentPointList = currentPointList;
    }

    public Map<BigDecimal, List<GamePlayer>> getEscapeTempMap() {
        return escapeTempMap;
    }

    public void setEscapeTempMap(Map<BigDecimal, List<GamePlayer>> escapeTempMap) {
        this.escapeTempMap = escapeTempMap;
    }

    public List<GamePlayer> getEscapeTempList() {
        return escapeTempList;
    }

    public void setEscapeTempList(List<GamePlayer> escapeTempList) {
        this.escapeTempList = escapeTempList;
    }
    /**
     * 检查爆点主方法
     * @return
     */
    public boolean checkBurst() {

//        logger.info("---爆点检查---");

        if (this.getMode() == Constants.MODE_KILL || this.getMode() == Constants.MODE_PROBABILITY) {
            return this.getCurrentPoint().compareTo(this.getExpectedPoint()) >= 0;
        } else if (this.getMode() == Constants.MODE_COLLECT) {

            if (this.getPrize() == null || this.getPrize().compareTo(new BigDecimal(0)) == 0) {
                return RandomUtils.nextInt(0, 100) > 90;
            }
            BigDecimal amount1 = this.getPrize().subtract(this.getEscapedAmount()).subtract(this.getExpectedValue());
            BigDecimal amount2 = amount1.subtract(this.getFirstApplyAmount());
            if (amount2.floatValue() > 0) {
                this.setLaterExplosion(amount2.compareTo(this.getAvgCapital().multiply(HALF)) < 0);
                return true;
            } else if (amount1.subtract(this.getMaxSingleStayAmount()).floatValue() > 0) {
                return true;
            }
            return false;
        } else if (this.getMode() == Constants.MODE_RELEASE) {
            return RandomUtils.nextInt(0, 100) < this.getBurstProbability();
        }

        return false;
    }

    /**
     * 计算增长点方法
     */
    public void increase() {
        if (count == 0) {
            this.setCurrentPoint(new BigDecimal(ConfigUtil.getCurrentPoint()));
        } else {
            this.setIncreaseRatio(this.getIncreaseRatio().multiply(this.getAcceleration()));
            this.setCurrentPoint(this.getCurrentPoint().add(this.getIncreaseRatio()).setScale(4, BigDecimal.ROUND_HALF_UP));
            this.getCurrentPointList().add(this.getCurrentPoint());
        }
        count += 1;
    }

    /**
     * 自动逃跑：
     * 1、每一次玩家最大能盈的钱是0.75% 也就是说当单个用户盈利超过奖池的0.75%的时候，就会自动逃跑成功
     * 2、所有用户的单次盈利不能超过奖池的1.25%。 也就是说当已逃跑收益+剩余资金*当前赔率大于1.25%的时候，所有玩家自动逃跑成功
     * 3、玩家还可以设定自己的自动逃跑的点，到了点数就自动逃跑
     *
     * @return
     */
    public void autoEscape() {
//        logger.debug("---自动逃跑处理--");
        Iterator<GamePlayer> iterator = stayedList.iterator();
        GamePlayer gamePlayer;
        while (iterator.hasNext()) {
            gamePlayer = iterator.next();
            // 机器人自动逃跑
            if (gamePlayer.isNpcPlayer()) {
                if (this.getCurrentPoint().compareTo(new BigDecimal(RandomUtils.nextFloat(1.1f, 30))) > 0) {
//                    logger.debug("机器人自动逃跑, ID：" + gamePlayer.getId() + "，金额：" + gamePlayer.getCapital());
                    if (ESCAPE_SUCCESS.equals(gamePlayer.getEscapeStatus())) {
                        continue;
                    }
                    dealAutoEscape(gamePlayer);
                    continue;
                }
            } else if (!ESCAPE_SUCCESS.equals(gamePlayer.getEscapeStatus())){

                // 1、每一次玩家最大能盈的钱是0.75% 也就是说当单个用户盈利超过奖池的0.75%的时候，就会自动逃跑成功
                if (gamePlayer.getCapital().multiply(this.getCurrentPoint())
                        .divide(this.getTotalBonusPool(), 2, BigDecimal.ROUND_DOWN).floatValue() >= this.getPersonalLimit()) {
                    dealAutoEscape(gamePlayer);
                    continue;
                }

                // 2、玩家还可以设定自己的自动逃跑的点，到了点数就自动逃跑
                if (BetStatusEnum.AUTO.getCode().equals(gamePlayer.getBetStatus())
                        && gamePlayer.getMultiple().compareTo(this.getCurrentPoint()) < 0) {
                    dealAutoEscape(gamePlayer);
                    continue;
                }
            }
        }
        if (this.getStayedList().size() > 0) {

            // 3、逃跑收益+剩余资金*(当前赔率-1) > 总奖池的1.25% 则所有人自动逃跑
            if (this.getEscapedAmount().add(this.getStayedAmount())
                    .multiply(this.getCurrentPoint().subtract(new BigDecimal("1")))
                    .compareTo(this.getCurrentPoint().multiply(this.getAutoEscapedLimit())) > 0) {
                while (iterator.hasNext()) {
                    gamePlayer = iterator.next();
                    if (!ESCAPE_SUCCESS.equals(gamePlayer.getEscapeStatus())) {
                        dealAutoEscape(gamePlayer);
                    }
                }
            }
        }
    }

    /**
     * 自动逃跑玩家赋值处理（公共方法）
     * @param player
     */
    private void dealAutoEscape(GamePlayer player) {
        player.setProfit(player.getCapital().multiply(this.getCurrentPoint()).subtract(player.getCapital()).setScale(2, BigDecimal.ROUND_HALF_UP));
        player.setEscapeStatus(EscapeStatusEnum.SUCCESS.getCode());
        player.setBetStatus(BetStatusEnum.AUTO.getCode());
        player.setEscapteTime(DateUtil.dateToStr(new Date()));
        player.setMultiple(this.getCurrentPoint());
        this.getEscapedList().add(player);
        this.getEscapeTempList().add(player);
        this.getEscapeTempMap().put(this.getCurrentPoint(), this.getEscapedList());
    }


    /**
     * 获取逃跑金额
     * @return
     */
    private BigDecimal getEscapedAmount() {
        BigDecimal amount = BigDecimal.ZERO;

        for (GamePlayer gamePlayer : this.getEscapedList()) {
            if (!gamePlayer.isNpcPlayer()) {
                amount = amount.add(gamePlayer.getCapital());
                amount = amount.add(gamePlayer.getProfit());
            }
        }
        amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
//        logger.debug("逃跑金额为:" + amount);
        return amount;
    }

    /**
     * 剩余金额
     * @return
     */
    private BigDecimal getStayedAmount() {
        BigDecimal amount = BigDecimal.ZERO;

        for (GamePlayer gamePlayer : getEscapedList()) {
            if (!gamePlayer.isNpcPlayer()) {
                amount = amount.add(gamePlayer.getCapital());
                amount = amount.add(gamePlayer.getCapital().multiply(this.getCurrentPoint()));
            }
        }
        amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
//        logger.debug("留存金额为:" + amount);
        return amount;
    }

    /**
     * 每一局游戏创建时初始化游戏
     * @param mode
     * @param issueNo
     * @param expectedPoint
     */
    public void initGame(int mode, Long issueNo, BigDecimal expectedPoint) {
        logger.debug("初始化当前游戏");
        this.setMode(mode);
        this.setIssueNo(issueNo);
        this.setExpectedPoint(expectedPoint);
    }

    /**
     * 下注玩家加入list
     *
     * @param status     当前期还是下一期
     * @param gamePlayer 下注玩家
     */
    public boolean initPlayer(int status, GamePlayer gamePlayer) {
        logger.debug("初始化玩家");
        boolean flag;
        if (Constants.CURRENT_GAME == status) {
            flag = setGamePlayer(this.getStayedList(), gamePlayer);
            this.setPrize(calcPlayerCapital(this.getStayedList()));
            logger.debug("当前局下注玩家数量：" + getStayedList().size());
        } else {
            flag = setGamePlayer(this.getNextGameBetList(), gamePlayer);
        }
        return flag;
    }

    /**
     * 同一局同一个玩家只能有一次下注
     *
     * @param list
     * @param gamePlayer
     */
    private boolean setGamePlayer(List<GamePlayer> list, GamePlayer gamePlayer) {
        if (list.size() > 0) {
            Iterator<GamePlayer> iterator = list.iterator();
            GamePlayer temp;
            while (iterator.hasNext()) {
                temp = iterator.next();
                if (temp.getId() == gamePlayer.getId() && temp.getIssueNo().equals(gamePlayer.getIssueNo())) {
                    logger.error("玩家重复下注：ID:" + gamePlayer.getId());
                    return false;
                }
            }
            list.add(gamePlayer);
            return true;
        } else {
            list.add(gamePlayer);
            return true;
        }
    }

    public BigDecimal getFirstApplyAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(this.getApplyList())) {
            GamePlayer gamePlayer = this.getApplyList().get(0);
            amount = amount.add(gamePlayer.getCapital());
            amount = amount.add(gamePlayer.getCapital().multiply(this.getCurrentPoint()));
        }
        amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
        return amount;
    }

    private BigDecimal getMaxSingleStayAmount() {
        BigDecimal amount = BigDecimal.ZERO;

        for (GamePlayer gamePlayer : this.getStayedList()) {
            if (!gamePlayer.isNpcPlayer()) {
                BigDecimal stayAmount = gamePlayer.getCapital().add(gamePlayer.getCapital().multiply(this.getCurrentPoint()));
                if (stayAmount.compareTo(amount) > 0) {
                    amount = stayAmount;
                }
            }
        }
        amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
        return amount;
    }

    private BigDecimal getAvgCapital() {
        BigDecimal amount = BigDecimal.ZERO;

        int i = 0;
        for (GamePlayer gamePlayer : this.getStayedList()) {
            if (!gamePlayer.isNpcPlayer()) {
                amount = amount.add(gamePlayer.getCapital());
                i += 1;
            }
        }

        int j = 0;
        for (GamePlayer gamePlayer : this.getEscapedList()) {
            if (!gamePlayer.isNpcPlayer()) {
                amount = amount.add(gamePlayer.getCapital());
                j += 1;
            }
        }

        amount = amount.divide(new BigDecimal(i + j));
        amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
        return amount;
    }


    /**
     * 如果是爆炸点的处理
     * 1、清空所有列表中的NPC玩家，不计算盈亏，清除所有已逃跑玩家
     * 2、处理已逃跑列表中的玩家
     * 3、处理未逃跑列表中的玩家
     * 4、修改当前局游戏
     * 5、处理对下一局下注的玩家
     * 6、初始化参数
     */
    public void dealIsBurstPoint() {
        logger.debug("到达爆炸点" + getCurrentPoint());
        removeNpc(this.getEscapedList());
        if (this.getEscapedList() != null && this.getEscapedList().size() > 0) {
            SpringContextHolder.getBean(BetRecordService.class).dealAfterBurstPoint(this.getEscapedList());
        }
        removeNpc(this.getStayedList());
        removeSuccessPlayer(this.getStayedList());
        if (this.getStayedList() != null && this.getStayedList().size() > 0) {
            Iterator<GamePlayer> iterator = this.getStayedList().iterator();
            GamePlayer player;
            while (iterator.hasNext()) {
                player = iterator.next();
                player.setProfit(new BigDecimal(0).subtract(player.getCapital()));
                player.setEscapeStatus(EscapeStatusEnum.FAILED.getCode());
            }
            setMultipleAfterBurst(this.getStayedList());
            SpringContextHolder.getBean(BetRecordService.class).dealAfterBurstPoint(this.getStayedList());
            this.getStayedList().clear();
        }
        Game game = new Game();
        game.setIssueNo(Long.valueOf(this.getIssueNo()));
        if (this.getMode() != Constants.MODE_KILL && this.getMode() != Constants.MODE_PROBABILITY) {
            game.setBurstPoint(this.getCurrentPoint().setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
        } else {
            this.setCurrentPoint(this.getExpectedPoint());
        }
        game.setEndTime(DateUtil.dateToStr(new Date()));
        Double playerProfit = calcPlayerProfit(this.getEscapedList()).doubleValue();
        game.setPlayerProfit(playerProfit);
        this.setPrize(this.getPrize() == null ? new BigDecimal(0) : this.getPrize());
        BigDecimal platFormProfit = calcPlayerProfit(this.getStayedList()).subtract(calcPlayerProfit(this.getEscapedList()));
        game.setPlatformProfit(NumberUtils.toDouble(platFormProfit));
        this.setTotalBonusPool(this.getTotalBonusPool().add(platFormProfit));
        SpringContextHolder.getBean(GameService.class).dealAfterBurst(game);
        if (this.getNextGameBetList() != null && this.getNextGameBetList().size() > 0) {
            this.getStayedList().addAll(this.getNextGameBetList());
            this.setPrize(calcPlayerCapital(this.getStayedList()));
            SpringContextHolder.getBean(BetRecordService.class).dealAfterBurstPoint(this.getStayedList());
            this.getNextGameBetList().clear();
        }
        try {
            Thread.sleep(ConfigUtil.getShowPointDuration());
        } catch (InterruptedException e) {
            logger.error("游戏错误");
        }
        initParam();
    }

    /**
     * 爆炸后为没有逃跑的用户设置爆炸点
     * @param list
     */
    private void setMultipleAfterBurst(List<GamePlayer> list) {
        for (GamePlayer player : list) {
            player.setMultiple(this.getCurrentPoint().setScale(4, BigDecimal.ROUND_HALF_UP));
        }
    }

    /**
     * 爆炸后初始化基本参数
     */
    public void initParam() {
        logger.debug("------初始化参数------");
        this.setCurrentPoint(new BigDecimal(ConfigUtil.getCurrentPoint()));
        this.setIncreaseRatio(new BigDecimal(ConfigUtil.getIncreaseRatio()));
        this.setCount(0);
        this.setIssueNo(this.issueNo + 1L);
        this.getEscapedList().clear();
        this.getCurrentPointList().clear();
    }

    /**
     * 处理玩家逃跑的情况：将用户加入已逃跑列表，并从原来停留列表中移除
     *
     * @param playerId
     */
    public void dealNotBurstPoint(int playerId) {
        if (this.getStayedList() != null && this.getStayedList().size() > 0) {
            GamePlayer player;
            Iterator<GamePlayer> iterator = this.getStayedList().iterator();
            while (iterator.hasNext()) {
                player = iterator.next();
                if (player.getId() == playerId) {
                    player.setProfit(player.getCapital().multiply(this.getCurrentPoint()).subtract(player.getCapital()).setScale(2, BigDecimal.ROUND_HALF_UP));
                    player.setEscapeStatus(EscapeStatusEnum.SUCCESS.getCode());
                    player.setBetStatus(BetStatusEnum.MANUAL.getCode());
                    player.setEscapteTime(DateUtil.dateToStr(new Date()));
                    player.setMultiple(this.getCurrentPoint());
                    this.getEscapedList().add(player);
                    this.getEscapeTempList().add(player);
                    this.getEscapeTempMap().put(this.getCurrentPoint(), this.getStayedList());
                }
            }
        }
    }


    /**
     * 计算玩家盈利
     * @param list
     * @return
     */
    private BigDecimal calcPlayerCapital(List<GamePlayer> list) {
        if (list == null || list.size() == 0) {
            return new BigDecimal(0);
        }
        List<GamePlayer> temp = new ArrayList<>();
        for (GamePlayer player : list) {
            if (!player.isNpcPlayer()) {
                temp.add(player);
            }
        }
        if (temp.size() == 0) {
            return new BigDecimal(0);
        }
        this.setPrize(temp.stream().map(GamePlayer::getCapital).reduce(BigDecimal::add).get());
        return this.getPrize();
    }

    /**
     * 对已逃跑列表中的盈利额求和
     *
     * @param list
     * @return
     */
    private BigDecimal calcPlayerProfit(List<GamePlayer> list) {
        if (list == null || list.size() == 0) {
            return new BigDecimal(0);
        }
        return list.stream().map(GamePlayer::getProfit).reduce(BigDecimal::add).get()
                .setScale(4, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * 创建游戏后添加NPC玩家
     */
    public void increaseNpcPlayer() {
        List<Player> players = (List<Player>) SpringContextHolder.getBean(CacheBase.class).getNpcPlayer();
        if (players == null) {
            return;
        }
        int calcParam = RandomUtils.nextInt(1, players.size() / 2);
        Collections.shuffle(players, new Random(calcParam));
        for (int i = calcParam - 1; i < calcParam; i++) {
            GamePlayer player = new GamePlayer();
            player.setPlayerName(players.get(i).getPlayerName());
            player.setId(players.get(i).getId());
            player.setNpcPlayer(true);
            player.setIssueNo(this.getIssueNo());
            player.setCapital(new BigDecimal(RandomUtils.nextInt(1, 30) * 10).setScale(2, BigDecimal.ROUND_HALF_UP));
            logger.debug("NPC, ID: " + players.get(i).getId());
            initPlayer(Constants.CURRENT_GAME, player);
        }
    }

    /**
     * 爆炸结算时去除所有的NPC玩家
     *
     * @param list
     */
    private void removeNpc(List<GamePlayer> list) {
        GamePlayer player;
        Iterator<GamePlayer> iterator = list.iterator();
        while (iterator.hasNext()) {
            player = iterator.next();
            if (player.isNpcPlayer()) {
                iterator.remove();
            }
        }
    }

    /**
     * 从总表中去除所有已逃跑的玩家
     * @param list
     */
    private void removeSuccessPlayer(List<GamePlayer> list) {
        GamePlayer player;
        Iterator<GamePlayer> iterator = list.iterator();
        while (iterator.hasNext()) {
            player = iterator.next();
            if (ESCAPE_SUCCESS.equals(player.getEscapeStatus())) {
                iterator.remove();
            }
        }
    }

}
