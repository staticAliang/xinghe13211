package com.fengshen.server.data.vo;

// 宠物飞升，发送到前端特效中显示的属性
public class Vo_MSG_PET_UPGRADE_SUCC {
    public  int id = 0;//宠物ID
    public  int[] pet_life_shape = new int[2];
    public  int[] pet_mana_shape = new int[2];
    public  int[] pet_speed_shape= new int[2];
    public  int[] pet_phy_shape  = new int[2];
    public  int[] pet_mag_shape  = new int[2];
}
