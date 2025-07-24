package com.fengshen.core.util;

import java.util.*;
// 响应实体
public class ResponseUtil
{
    public static Object ok() {
        final Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", 0);
        obj.put("msg", "成功");
        return obj;
    }

    public static Object ok(final Object data) {
        final Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", 0);
        obj.put("msg", "成功");
        obj.put("data", data);
        return obj;
    }

    public static Object ok(final String errmsg, final Object data) {
        final Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", 0);
        obj.put("msg", errmsg);
        obj.put("data", data);
        return obj;
    }

    public static Object fail() {
        final Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", -1);
        obj.put("msg", "错误");
        return obj;
    }

    public static Object fail(final int errno, final String errmsg) {
        final Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("code", errno);
        obj.put("msg", errmsg);
        return obj;
    }

    public static Object badArgument() {
        return fail(401, "参数不对");
    }

    public static Object badArgument(final String str) {
        return fail(401, "参数不对," + str);
    }

    public static Object badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static Object unlogin() {
        return fail(501, "请登录");
    }

    public static Object serious() {
        return fail(502, "系统内部错误");
    }

    public static Object unsupport() {
        return fail(503, "业务不支持");
    }

    public static Object updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static Object updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static Object unauthz() {
        return fail(506, "无操作权限");
    }

    public static Object notimes() {
        return fail(507, "查询次数用尽");
    }

    public static Object errEmail() {
        return fail(800, "请输入正确的邮箱!");
    }

    public static Object errPhone() {
        return fail(801, "请输入正确的电话号码!");
    }

    public static Object errMobilePhone() {
        return fail(802, "请输入正确的手机号码!");
    }

    public static Object errInputEmpty() {
        return fail(803, "请填入必填项!");
    }
}