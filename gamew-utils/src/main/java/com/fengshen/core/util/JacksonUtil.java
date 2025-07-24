package com.fengshen.core.util;

import org.slf4j.*;
import java.io.*;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.core.*;

public class JacksonUtil
{
    private static final Logger logger;
    
    static {
        logger = LoggerFactory.getLogger((Class)JacksonUtil.class);
    }
    
    public static String parseString(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asText();
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("解析字为符串失败", e);
        }
        return null;
    }
    
    public static List<String> parseStringList(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                return (List<String>)mapper.convertValue((Object)leaf, (TypeReference)new TypeReference<List<String>>() {});
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为字符串列表失败", (Throwable)e);
        }
        return null;
    }
    
    public static Integer parseInteger(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asInt();
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为整数失败", (Throwable)e);
        }
        return null;
    }
    
    public static List<Integer> parseIntegerList(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                return (List<Integer>)mapper.convertValue((Object)leaf, (TypeReference)new TypeReference<List<Integer>>() {});
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为整数列表失败", (Throwable)e);
        }
        return null;
    }
    
    public static Boolean parseBoolean(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                return leaf.asBoolean();
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为布尔值失败", (Throwable)e);
        }
        return null;
    }
    
    public static Short parseShort(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                final Integer value = leaf.asInt();
                return (short)(Object)value;
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为短整形失败", (Throwable)e);
        }
        return null;
    }
    
    public static Byte parseByte(final String body, final String field) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            final JsonNode leaf = node.get(field);
            if (leaf != null) {
                final Integer value = leaf.asInt();
                return (byte)(Object)value;
            }
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为字节失败", (Throwable)e);
        }
        return null;
    }
    
    public static <T> T parseObject(final String body, final String field, final Class<T> clazz) {
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(body);
            node = node.get(field);
            return (T)mapper.treeToValue((TreeNode)node, (Class)clazz);
        }
        catch (IOException e) {
            JacksonUtil.logger.error("字符串解析为对象失败", (Throwable)e);
            return null;
        }
    }
    
    public static Object toNode(final String json) {
        if (json == null) {
            return null;
        }
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final JsonNode jsonNode = mapper.readTree(json);
            return jsonNode;
        }
        catch (IOException e) {
            JacksonUtil.logger.error("Json转换成对象失败", (Throwable)e);
            return null;
        }
    }
}
