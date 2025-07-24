package com.fengshen.server.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 */
public class RandomUtil {


    public static boolean checkLianji(float rate) {
        rate = (rate - 0.8f > 0) ? 0.8f : rate;
        double randomNum = ThreadLocalRandom.current().nextInt(100);
        return (randomNum <= (int)(rate * 100)) ? true : false;
    }
    // 普通攻击连击率算法（1%+装备的必杀率%）如果装备必杀率>80%取80%
    public static boolean checkBisha(float rate) {
        rate = (rate - 0.8f > 0) ? 0.8f : rate;
        double randomNum = ThreadLocalRandom.current().nextInt(100);
        return (randomNum <= (int)(rate * 100)) ? true : false;
    }
    public static boolean checkFanji(float rate) {
        rate = (rate - 0.8f > 0) ? 0.8f : rate;
        double randomNum = ThreadLocalRandom.current().nextInt(100);
        return (randomNum <= (int)(rate * 100)) ? true : false;
    }
    
    public static boolean checkFanzhen(float rate) {
        rate = (rate - 0.8f > 0) ? 0.8f : rate;
        double randomNum = ThreadLocalRandom.current().nextInt(100);
        return (randomNum <= (int)(rate * 100)) ? true : false;
    }
    
    
    /**
     * 是否闪躲
     * @param rate
     * @return
     */
    public static boolean checkMagDodge(float rate) {
    	//如果大于80最高只能设为80
    	if(rate>100) {
    		rate = 100;
    	}
    	int value = 95;
    	if(rate >= 10 && rate<=20) {
    		value = 10;
    	}else if(rate >= 21 && rate<=40) {
    		value = 20;
    	}else if(rate >= 41 && rate<=60) {
    		value = 30;
    	}else if(rate >= 61 && rate<=80) {
    		value = 40;
    	}else if(rate >= 81 && rate<=100) {
    		value = 50;
    	}
    	if(rate<=0) {
    		return false;
    	}
        double randomNum = ThreadLocalRandom.current().nextInt(100);
        if(ThreadLocalRandom.current().nextBoolean()) {
        	 return value<randomNum?true:false;
        }
        return false;
    }

    /**
     * 随机百分比概率是否成功
     * @param rate 百分比
     * @return
     */
    public static boolean checkSuccess(float rate){
        double randomNum = ThreadLocalRandom.current().nextInt(10000);
        randomNum = randomNum + 1;
        return rate * 10000 >= randomNum;
    }

    /**
     * 生成随机数 [0, max)
     * @param max
     * @return
     */
    public static int randomInt(int max){
        return ThreadLocalRandom.current().nextInt(max);
    }
    /**
     * 生成随机数 [1, max]
     * @param max
     * @return
     */
    public static int randomNotZeroInt(int max){
        return randomInt(max)+1;
    }
    /**
     * 生成随机数 [min, max)
     * @param max
     * @return
     */
    public static int randomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
