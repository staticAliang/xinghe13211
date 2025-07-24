package com.fengshen.server.data.vo;

import java.util.List;

import com.fengshen.db.domain.ChargePoint;

// 对应重置积分商品的详细信息
public class Vo_53477_0 {
    public int startTime;
    public int endTime;
    public int deadline;
    /**拥有的积分*/
    public int ownPoint;
    /**累计的积分*/
    public int totalPoint;
    public List<ChargePoint> items;
}
