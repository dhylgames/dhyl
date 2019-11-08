package com.yd.burst.util;

import com.yd.burst.dao.ConfigMapper;
import com.yd.burst.model.Config;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class ConfigUtil {

    private static Logger log = LogManager.getLogger(ConfigUtil.class);

    private static final Map<String, Config> CFG = new HashMap();

    public static final String MODE_WEIGHT_KILL = "MODE_WEIGHT_KILL"; // 通杀

    public static final String MODE_WEIGHT_COLLECT = "MODE_WEIGHT_COLLECT"; // 普通 (收水)

    public static final String MODE_WEIGHT_RELEASE = "MODE_WEIGHT_RELEASE"; // 放水

    public static final String MODE_WEIGHT_PROBABILITY = "MODE_WEIGHT_PROBABILITY"; // 概率

    public static final String BURST_POINT_FOR_KILL = "BURST_POINT_FOR_KILL";

    public static final String INCREASE_RATIO = "increaseRatio";

    public static final String CURRENT_POINT = "currentPoint";

    public static final String TOTAL_LIMIT = "totalLimit";

    public static final String PERSONAL_LIMIT = "personalLimit";

    public static final String BURST_PROBABILITY = "burstProbability";

    public static final String TOTAL_AMOUNT_WEIGHT = "totalAmountWeight";

    public static final String TOTAL_AMOUNT_PARAM = "totalAmountParam";

    public static final String MIN_AMOUNT = "minAmount";

    public static final String TOTAL_AMOUNT = "totalAmount";

    public static final String AUTO_ESCAPED_LIMIT = "autoEscapedLimit";

    public static final String ACCELERATION = "acceleration";

    public static final String CALC_POINT_INTERVAL = "CALC_POINT_INTERVAL";

    public static final String SHOW_POINT_DURATION = "SHOW_POINT_DURATION";

    public static final String FRONT_SYNC_INTERVAL = "FRONT_SYNC_INTERVAL";

    public static final String CHARGE_ADDRESS = "CHARGE_ADDRESS";

    public static final String BUSINESS_KEY = "BUSINESS_KEY";

    public static final String BUSINESS_CODE = "BUSINESS_CODE";

    public static final String PAY_DETAIL = "PAY_DETAIL";

    public static final String FRONT_REFRESH_INTERVAL = "FRONT_REFRESH_INTERVAL";


    private static ConfigMapper getMapper() {
        return SpringContextHolder.getBean(ConfigMapper.class);
    }

    public static Map<String, Config> getConfig() {
        if (CFG.size() == 0) {
            init();
        }
        return CFG;
    }

    public static void init() {
        if (CFG.size() == 0) {
            for (Config o : getMapper().selectAllConfig()) {
                CFG.put(o.getKey(), o);
            }
        }
    }


    public static List<Config> getListBySubject(String subject) {
        List<Config> list = new ArrayList();
        Iterator iterator = getConfig().values().iterator();

        while (iterator.hasNext()) {
            Config config = (Config) iterator.next();
            if (subject.equalsIgnoreCase(config.getSubject())) {
                list.add(config);
            }
        }
        return list;
    }

    public Map<String, Config> getMapBySubject(String subject) {
        Map<String, Config> map = new HashMap();
        Iterator iterator = getConfig().values().iterator();

        while (iterator.hasNext()) {
            Config config = (Config) iterator.next();
            if (subject.equalsIgnoreCase(config.getSubject())) {
                map.put(config.getKey(), config);
            }
        }

        return map;
    }

    /**
     * 获取游戏模式
     */
    public static int getGameMode() {
        int modeWeightKill = NumberUtils.toInt(getConfig().get(MODE_WEIGHT_KILL).getValue1());
        int modeWeightNormal = NumberUtils.toInt(getConfig().get(MODE_WEIGHT_COLLECT).getValue1());
        int modeWeightRelease = NumberUtils.toInt(getConfig().get(MODE_WEIGHT_RELEASE).getValue1());
        int modeWeightProbability = NumberUtils.toInt(getConfig().get(MODE_WEIGHT_PROBABILITY).getValue1());

        int result = RandomUtils.nextInt(0, modeWeightKill + modeWeightNormal + modeWeightRelease + modeWeightProbability);
        if (result < modeWeightKill) {
            return Constants.MODE_KILL;
        } else if (result < (modeWeightKill + modeWeightNormal)) {
            return Constants.MODE_COLLECT;
        } else if (result < (modeWeightKill + modeWeightNormal + modeWeightRelease)) {
            return Constants.MODE_RELEASE;
        } else {
            return Constants.MODE_PROBABILITY;
        }
    }

    /**
     * 获取通杀局爆点
     */
    public static float getBurstPoint() {
        List<Config> configList = getListBySubject(BURST_POINT_FOR_KILL);
        int sum = 0;
        for (Config config : configList) {
            sum += NumberUtils.toInt(config.getValue1());
        }

        float result = RandomUtils.nextFloat(0f, sum);
        sum = 0;
        for (Config config : configList) {
            sum += NumberUtils.toInt(config.getValue1());
            if (result < sum) {
                return RandomUtils.nextFloat(NumberUtils.toFloat(config.getValue2()), NumberUtils.toFloat(config.getValue3()));
            }
        }
        return 1f;
    }

//    public static BigDecimal randomBurstPoint() {
//        int count = 1;
//        boolean findBurstPoint = false;
//        float random = RandomUtils.nextFloat(0, 1) * 1.05f;
//
//        while (!findBurstPoint) {
//            if (random > (1 / (1 + count * 0.01))) {
//                findBurstPoint = true;
//            }
//            count++;
//        }
//        return new BigDecimal(1 + (count * 0.01f));
//    }

    public static BigDecimal randomBurstPoint() {
        int i = 1;
        float p = 1f;
        float px;
        float r;
        boolean isBurstPoint = false;
        BigDecimal burstPoint = BigDecimal.ZERO;

        while (!isBurstPoint) {
            r = RandomUtils.nextFloat(0.1f, 1);
            px = 1 / (1 + i * 0.01f) / p;
            p = p * px;
            if (r > px) {
                burstPoint = new BigDecimal(1 + (i - 1) * 0.01f);
                burstPoint.setScale(4, BigDecimal.ROUND_HALF_UP);
                isBurstPoint = true;
                //System.out.println("当i=" + i + "时，px=" + px + " | r=" + r);
            }
            i++;
        }

        return burstPoint;
    }


    public static float getIncreaseRatio() {
        return NumberUtils.toFloat(getConfig().get(INCREASE_RATIO).getValue1());
    }

    public static String getCurrentPoint() {
        return getConfig().get(CURRENT_POINT).getValue1();
    }

    public static String getTotalLimit() {
        return getConfig().get(TOTAL_LIMIT).getValue1();
    }

    public static String getPersonalLimit() {
        return getConfig().get(PERSONAL_LIMIT).getValue1();
    }

    public static String getBurstProbability() {
        return getConfig().get(BURST_PROBABILITY).getValue1();
    }

    public static String getTotalAmountWeight() {
        return getConfig().get(TOTAL_AMOUNT_WEIGHT).getValue1();
    }

    public static String getTotalAmountParam() {
        return getConfig().get(TOTAL_AMOUNT_PARAM).getValue1();
    }

    public static String getTotalAmount() {
        return getConfig().get(TOTAL_AMOUNT).getValue1();
    }

    public static String getMinAmount() {
        return getConfig().get(MIN_AMOUNT).getValue1();
    }

    public static String getAutoEscapedLimit() {
        return getConfig().get(AUTO_ESCAPED_LIMIT).getValue1();
    }

    public static String getAcceleration() {
        return getConfig().get(ACCELERATION).getValue1();
    }

    public static long getCalcPointInterval() {
        return Long.valueOf(getConfig().get(CALC_POINT_INTERVAL).getValue1());
    }

    public static long getFrontSyncInterval() {
        return Long.valueOf(getConfig().get(FRONT_SYNC_INTERVAL).getValue1());
    }

    public static long getShowPointDuration() {
        return Long.valueOf(getConfig().get(SHOW_POINT_DURATION).getValue1());
    }

    public static String getChargeAddress () {
        return getConfig().get(CHARGE_ADDRESS).getValue1();
    }

    public static String getBusinessKey () {
        return getConfig().get(BUSINESS_KEY).getValue1();
    }

    public static String getBusinessCode () {
        return getConfig().get(BUSINESS_CODE).getValue1();
    }

    public static String getPayDetail () {
        return getConfig().get(PAY_DETAIL).getValue1();
    }

    public static String getFrontRefreshInterval() {
        return getConfig().get(FRONT_REFRESH_INTERVAL).getValue1();
    }

    public static void reload() {
        CFG.clear();
    }



    public static void main(String[] args) {
        for (int j = 0; j < 2000; j++) {
            List<BigDecimal> lt1p1List = new ArrayList<>();
            List<BigDecimal> lt2List = new ArrayList<>();
            List<BigDecimal> lt4List = new ArrayList<>();
            List<BigDecimal> lt10List = new ArrayList<>();
            List<BigDecimal> lt40List = new ArrayList<>();
            List<BigDecimal> otherList = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                BigDecimal bp = randomBurstPoint();
                if (bp.floatValue() < 1.1f) {
                    lt1p1List.add(bp);
                } else if (bp.floatValue() < 2f) {
                    lt2List.add(bp);
                } else if (bp.floatValue() < 4f) {
                    lt4List.add(bp);
                } else if (bp.floatValue() < 10f) {
                    lt10List.add(bp);
                } else if (bp.floatValue() < 40f) {
                    lt40List.add(bp);
                } else {
                    otherList.add(bp);
                }
            }

//            Collections.sort(lt1p1List);
//            Collections.sort(lt2List);
//            Collections.sort(lt4List);
//            Collections.sort(lt10List);
//            Collections.sort(lt40List);
//            Collections.sort(otherList);
//
//            System.out.println("小于1.1的个数" + lt1p1List.size());
//            for (BigDecimal bp : lt1p1List) {
//                System.out.print(bp + " | ");
//            }
//            System.out.println();
//            System.out.println("大于1.1且小于2的个数" + lt2List.size());
//            for (BigDecimal bp : lt2List) {
//                System.out.print(bp + " | ");
//            }
//            System.out.println();
//            System.out.println("大于2且小于4的个数" + lt4List.size());
//            for (BigDecimal bp : lt4List) {
//                System.out.print(bp + " | ");
//            }
//            System.out.println();
//            System.out.println("大于4且小于10的个数" + lt10List.size());
//            for (BigDecimal bp : lt10List) {
//                System.out.print(bp + " | ");
//            }
//            System.out.println();
//            System.out.println("大于10且小于40的个数" + lt40List.size());
//            for (BigDecimal bp : lt40List) {
//                System.out.print(bp + " | ");
//            }
//            System.out.println();
//            System.out.println("其余的个数" + otherList.size());
//            for (BigDecimal bp : otherList) {
//                System.out.print(bp + " | ");
//            }

            System.out.println("小于2的个数" + (lt2List.size() + lt1p1List.size()));
        }
    }
}

