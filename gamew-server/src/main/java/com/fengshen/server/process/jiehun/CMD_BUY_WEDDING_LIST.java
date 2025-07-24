package com.fengshen.server.process.jiehun;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.WeddingList;
import com.fengshen.db.service.chara.WeddingListService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 礼单购买
 * 
 *
 */
@Service
@Slf4j
public class CMD_BUY_WEDDING_LIST implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		String weddinglist = GameReadTool.readString2(buff);
		String[] split = weddinglist.split(";");
		//查询出价格
		Example example = new Example(WeddingList.class);
		example.selectProperties("price");
		example.createCriteria().andIn("name", Lists.newArrayList(split));
		WeddingListService weddingListService = SpringBeanUtils.getBean(WeddingListService.class);
		List<WeddingList> selectByExample = weddingListService.selectByExample(example);
		//计算总价格
		int sum = selectByExample.stream().mapToInt(WeddingList::getPrice).sum();
		//获取消耗类型
		ConfigInfo config = GameData.that.configInfoService.getOneByKeyName("marry_cost_type");
		String type = "金元宝";
		if(config != null && !StringUtils.isNullOrEmpty(config.getData()) && ("积分".equals(config.getData())
				|| "金元宝".equals(config.getData()) || "银元宝".equals(config.getData()))) {
			type =  config.getData();
		}
		String msg = "你确定花费#R%d%s#n来举办此次婚礼吗？";
		Map<String,Object> data = new HashMap<>();
		data.put("type", type);
		data.put("sum", sum);
		data.put("weddinglist", weddinglist);
		gameObjectChar.confirmData = data;
		GameUtil.confirm(chara, String.format(msg, sum,type), "BUY_WEDDING_LIST");
		log.info("购买礼单：{}",weddinglist);
	}

	@Override
	public int cmd() {
		return 0xB070;
	}

}
