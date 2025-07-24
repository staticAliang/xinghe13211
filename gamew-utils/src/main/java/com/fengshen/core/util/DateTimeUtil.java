package com.fengshen.core.util;

import java.time.*;
import java.time.format.*;
import java.time.temporal.*;

public class DateTimeUtil
{
    public static String getDateTimeDisplayString(final LocalDateTime dateTime) {
        final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        final String strDate2 = dtf2.format(dateTime);
        return strDate2;
    }
}