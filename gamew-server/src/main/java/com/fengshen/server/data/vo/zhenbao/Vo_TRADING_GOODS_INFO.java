package com.fengshen.server.data.vo.zhenbao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_TRADING_GOODS_INFO {

	private String goodsId;

	private String sellerGid;

	private String goodsName;

	private Integer goodsType;

	private Integer state;

	private Integer endTime;
	private Integer price;
	private Integer icon;
	private Integer level;

	private Integer butout_price;
	private Integer sell_buy_type;
	private String appointee_name;
	private String appointee_gid;
	private String para;
}
