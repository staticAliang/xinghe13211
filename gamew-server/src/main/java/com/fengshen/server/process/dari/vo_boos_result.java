package com.fengshen.server.process.dari;

import lombok.Data;

@Data
public class vo_boos_result {
    private short old_rank; //旧排名
    private short new_rank; //新排名
    private short inside_rank; //排行榜排名
    private int add_damage; //旧伤害
    private int new_damage; //新伤害
}
