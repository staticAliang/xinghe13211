package com.fengshen.server.data.vo.zhenbao;

import java.util.List;

import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE.Vo_GOLD_STALL_MINE_Items;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_GOLD_STALL_GOODS_LIST {

	private Integer totalPage;

	private Integer cur_page;

	private List<Vo_GOLD_STALL_MINE_Items> items;

	private String path_str;
	private String select_gid;
	// 1 公示 2 逛摊
	private Integer sell_stage;
	//排序类型 "price" 按价格、"start_time" 按上架时间 (公示时间)
	private String sort_key;
	//是否降序
	private Integer is_descending;
}
