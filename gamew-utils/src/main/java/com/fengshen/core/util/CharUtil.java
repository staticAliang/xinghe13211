package com.fengshen.core.util;

import java.util.*;

public class CharUtil
{
    public static String getRandomString(final Integer num) {
        final String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        final Random random = new Random();
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; ++i) {
            final int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static String getRandomNum(final Integer num) {
        final String base = "0123456789";
        final Random random = new Random();
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; ++i) {
            final int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
