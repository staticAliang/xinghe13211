package com.fengshen.server.data.vo.zhenbao;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_GOLD_STALL_MINE {
	private int dealNum;
	private String sellCash;
	private int stallTotalNum;
	
	private List<Vo_GOLD_STALL_MINE_Items> items;
	
	@Getter
	@Setter
	public class Vo_GOLD_STALL_MINE_Items {
		private String name;
		private String goodsId;
		private Integer is_my_goods;
		//指定价格
		private int price;
		private int pos;
		//1公示 2正常出售 3超时
		private int status;
		private int startTime;
		private int endTime;
		private int level;
		//未鉴定
		private int unidentified;
		//要求等级
		private int req_level;
		//支付定金状态，0 - 未支付，1 - 已支付，2 - 不能支付，3 - 表示已经退还定金，4 - 表示已经没收定金
		//拓展字段 //{\"eclosion\":0,\"enchant\":0,\"mount_type\":0,\"rank\":4,\"rebuild_level\":0, \"deposit_state\":0}
		private String extra;
		//相性
		private int item_polar;
		private int cg_price_count;
		private int init_price;
		private int flag_num;
		//摆摊物品类型宠物、道具、金钱
		private int stall_item_type;
		//指定一口价
		private int buyout_price;
		//指定类型 0未指定 1指定、5拍卖
		private int sell_type;
		//指定人名称
		private String appointee_name;
		
	}
}
