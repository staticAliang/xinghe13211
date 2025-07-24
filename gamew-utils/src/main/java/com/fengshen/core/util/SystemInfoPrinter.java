package com.fengshen.core.util;

import java.util.*;

public class SystemInfoPrinter
{
    public static final String CREATE_PART_COPPER = "XOXOXOXOX";
    private static int maxSize;
    
    static {
        SystemInfoPrinter.maxSize = 0;
    }
    
    public static void printInfo(final String title, final Map<String, String> infos) {
        setMaxSize(infos);
        printHeader(title);
        for (final Map.Entry<String, String> entry : infos.entrySet()) {
            printLine(entry.getKey(), entry.getValue());
        }
        printEnd();
    }
    
    private static void setMaxSize(final Map<String, String> infos) {
        for (final Map.Entry<String, String> entry : infos.entrySet()) {
            if (entry.getValue() == null) {
                continue;
            }
            final int size = entry.getKey().length() + entry.getValue().length();
            if (size <= SystemInfoPrinter.maxSize) {
                continue;
            }
            SystemInfoPrinter.maxSize = size;
        }
        SystemInfoPrinter.maxSize += 30;
    }
    
    private static void printHeader(final String title) {
        System.out.println(getLineCopper());
        System.out.println("");
        System.out.println("              " + title);
        System.out.println("");
    }
    
    private static void printEnd() {
        System.out.println("  ");
        System.out.println(getLineCopper());
    }
    
    private static String getLineCopper() {
        String copper = "";
        for (int i = 0; i < SystemInfoPrinter.maxSize; ++i) {
            copper = String.valueOf(copper) + "=";
        }
        return copper;
    }
    
    private static void printLine(final String head, final String line) {
        if (line == null) {
            return;
        }
        if (head.startsWith("XOXOXOXOX")) {
            System.out.println("");
            System.out.println("    [[  " + line + "  ]]");
            System.out.println("");
        }
        else {
            System.out.println("    " + head + "        ->        " + line);
        }
    }
}
