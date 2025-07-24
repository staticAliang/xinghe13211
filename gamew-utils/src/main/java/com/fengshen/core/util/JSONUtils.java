package com.fengshen.core.util;

import com.fasterxml.jackson.databind.*;
import org.slf4j.*;

public class JSONUtils
{
    private static final ObjectMapper mapper;
    private static Logger log;
    
    static {
        mapper = new ObjectMapper();
        JSONUtils.log = LoggerFactory.getLogger((Class)JSONUtils.class);
    }
    
    public static String toJSONString(final Object data) {
        String str = null;
        try {
            str = JSONUtils.mapper.writeValueAsString(data);
        }
        catch (Exception e) {
            JSONUtils.log.error("data: " + data, (Throwable)e);
        }
        return str;
    }
    
    public static <T> T parseObject(final String jsonData, final Class<T> beanType) {
        try {
            final T t = (T)JSONUtils.mapper.readValue(jsonData, (Class)beanType);
            return t;
        }
        catch (Exception e) {
            JSONUtils.log.error("data: " + jsonData, (Throwable)e);
            return null;
        }
    }
}
