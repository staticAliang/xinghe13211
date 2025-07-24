package com.fengshen.server.domain.rank;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rank {

    private int id;

    private String uuid; //角色ID

    private String name; //名字

    private int level; //角色等级

    private int petId; //宠物ID

    private int petName; //宠物名称

    private int petLevel; //宠物等级

    private String type; //排行榜类型

    private int sortIdx; //排行

    private int value; //排行值

    private int polar; //门派

    private String partyName; //帮派名称

    private Date createTime; //创建日期
    
    private int upgrade_level;
    
    private int upgrade_type;

	public Rank() {
		
	}
}