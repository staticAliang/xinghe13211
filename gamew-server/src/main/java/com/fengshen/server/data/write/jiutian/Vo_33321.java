package com.fengshen.server.data.write.jiutian;

public class Vo_33321 {
    public int curCheckpoint;// = pkt:GetChar()  -- 当前可挑战第几关，从 0 开始计算，大于等于 openMax 则说明通关了
    public int openMax;// = pkt:GetChar()       -- 开放光卡最大值，从1开始
    public int is_open;// = pkt:GetChar()       -- 是否开启界面
}
