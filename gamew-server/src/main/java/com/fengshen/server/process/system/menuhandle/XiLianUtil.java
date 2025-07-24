package com.fengshen.server.process.system.menuhandle;

//import cn.hutool.core.util.RandomUtil;
import com.fengshen.server.util.GameConfig;
import io.netty.util.internal.ThreadLocalRandom;

public class XiLianUtil {


    public static int getRandomAttr() {
        int maxValue = GameConfig.config.getBaseConfig().getXiLianMaxValue();
        int baseNumber = GameConfig.config.getBaseConfig().getXiLianLBaseNumber();
        final double randomLevel = ThreadLocalRandom.current().nextDouble((baseNumber == 0) ? 100 : baseNumber);
//        final double randomLevel = RandomUtil.randomDouble(0, 100 * baseNumber);
        double l4Probability = GameConfig.config.getBaseConfig().getXiLianL4Probability();
        double l3Probability = GameConfig.config.getBaseConfig().getXiLianL3Probability();
        double l2Probability = GameConfig.config.getBaseConfig().getXiLianL2Probability();
        double l1Probability = GameConfig.config.getBaseConfig().getXiLianL1Probability();
        int avgVaue = maxValue / 5;
        //t value = RandomUtil.randomInt(0, avgVaue * 1);
       int value  = ThreadLocalRandom.current().nextInt(0,avgVaue * 1);
        if (randomLevel < l1Probability) {
            value = ThreadLocalRandom.current().nextInt(avgVaue * 4, maxValue);
        } else if (randomLevel < l2Probability) {
            value = ThreadLocalRandom.current().nextInt(avgVaue * 3, avgVaue * 4);
        } else if (randomLevel < l3Probability) {
            value = ThreadLocalRandom.current().nextInt(avgVaue * 2, avgVaue * 3);
        } else if (randomLevel < l4Probability) {
            value = ThreadLocalRandom.current().nextInt(avgVaue, avgVaue * 2);

        }
        return value + 1;
    }
}
