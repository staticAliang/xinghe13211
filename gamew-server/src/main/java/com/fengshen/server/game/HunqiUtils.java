package com.fengshen.server.game;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HunqiUtils {
	
    public static List<Hashtable<String, Object>> chuShihua() {
        List<Hashtable<String, Object>> zongShuxing = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Hashtable<String, Object> ziShuXing = new Hashtable<>();
            ziShuXing.put("chaos_value", 0);
            ziShuXing.put("yang_percent", 0);
            ziShuXing.put("yang_prop", "");
            ziShuXing.put("yang_prop_value", 0);
            ziShuXing.put("yin_prop", "");
            ziShuXing.put("yin_prop_value", 0);
            zongShuxing.add(ziShuXing);
        }
        return zongShuxing;
    }

    /**
     * 魂器阳属性
     * @param replaceAttr
     * @return
     */
    public static String horcrux_yang(String... replaceAttr) {
        Random random = new Random();
        String[] attr = {"phy_power", "mag_power", "speed", "str", "wiz", "dex", "penetrate", "penetrate_rate", "double_hit", "double_hit_rate", "ignore_resist_metal", "ignore_resist_wood", "ignore_resist_water", "ignore_resist_fire", "ignore_resist_earth", "ignore_all_resist_polar", "ignore_all_resist_except", "ignore_resist_forgotten", "ignore_resist_poison", "ignore_resist_frozen", "ignore_resist_sleep", "ignore_resist_confusion"};
        List<String> attrs = Lists.newArrayList(attr);
        for(String s:replaceAttr) {
        	if(attrs.contains(s)) {
        		attrs.remove(s);
        	}
        }
        return attrs.get(random.nextInt(attrs.size()));
    }

    /**
     * 魂器阴属性
     * @param replaceAttr
     * @return
     */
    public static String horcrux_yin(String... replaceAttr) {
        Random random = new Random();
        String[] attr = {"def", "max_life", "max_mana", "con", "damage_sel", "damage_sel_rate", "counter_attack", "counter_attack_rate", "resist_metal", "resist_wood", "resist_water", "resist_fire", "resist_earth", "all_resist_polar", "all_resist_except", "resist_forgotten", "resist_poison", "resist_frozen", "resist_sleep", "resist_confusion"};
        List<String> attrs = Lists.newArrayList(attr);
        for(String s:replaceAttr) {
        	if(attrs.contains(s)) {
        		attrs.remove(s);
        	}
        }
        return attrs.get(random.nextInt(attrs.size()));
    }

    public static int jisuanYang(int chaos_value, int yang_percent, String yang, int level) {
        JSONObject jsonObject = GameCore.hunqiYang.get(String.valueOf(level));
        float yangValue = chaos_value * yang_percent / 100;
        Integer jizhun = jsonObject.getIntValue(yang);
        int jg = Math.round(yangValue / 70 * jizhun);
        if(jg>jizhun) {
        	jg = jizhun;
        }
        return jg<=0?1:jg;
    }

    public static int jisuanYin(int chaos_value, int yin_percent, String yin, int level) {
        JSONObject jsonObject = GameCore.hunqiYin.get(String.valueOf(level));
        float yinValue1 = chaos_value * yin_percent / 100;
        float yinValue = chaos_value - yinValue1 / 2;
        Integer jizhun = jsonObject.getIntValue(yin);
        int jg = Math.round(yinValue / 70 * jizhun);
        if(jg>jizhun) {
        	jg = jizhun;
        }
        return jg<=0?1:jg;
    }

    public static Object dynamicGetValue(Object obj, String fieldName) {
        try {
            // 取属性首字母转大写
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            // get方法名
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            // 获取get方法
            Method getMethod = obj.getClass().getDeclaredMethod(getMethodName);
            // 动态取值
            return getMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object dynamicSetValue(Object obj, String fieldName, Object value) {
        try {
            // 取属性首字母转大写
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            // set方法名
            String setMethodName = "set" + firstLetter + fieldName.substring(1);
            // 获取属性
            Field field = obj.getClass().getDeclaredField(fieldName);
            // 获取set方法
            Method setMethod = obj.getClass().getDeclaredMethod(setMethodName, field.getType());
            // 通过set方法动态赋值
            setMethod.invoke(obj, value);
        } catch (Exception e) {
        	log.error("{}", e);
        }
        return obj;
    }
    
    public static void main(String[] args) {
    	int jisuanYang = jisuanYang(70,100,"",75);
    	System.out.println(jisuanYang);
	}

}
