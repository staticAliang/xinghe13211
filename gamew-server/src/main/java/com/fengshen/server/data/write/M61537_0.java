package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.ListVo_61537_0;
import com.fengshen.server.data.vo.Vo_61537_0;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M61537_0 extends BaseWrite<ListVo_61537_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, ListVo_61537_0 vo_61537_0) {
		GameWriteTool.writeShort(writeBuf, vo_61537_0.severState);
		GameWriteTool.writeShort(writeBuf, vo_61537_0.count);
		for (Vo_61537_0 vo : vo_61537_0.vo_61537_0) {
			GameWriteTool.writeShort(writeBuf, 17);
			// 263
			BuildFieldsNew.get("left_time_to_delete").write(writeBuf, vo.extra_desc);
			// 435
			BuildFieldsNew.get("char_online_state").write(writeBuf, vo.char_online_state);
			// 428
			BuildFieldsNew.get("trading_goods_gid").write(writeBuf, vo.trading_state);
			// 86
			BuildFieldsNew.get("portrait").write(writeBuf, vo.passive_mode);
			// 429
			BuildFieldsNew.get("trading_state").write(writeBuf, vo.trading_left_time);
			// 437
			BuildFieldsNew.get("trading_appointee_name").write(writeBuf, vo.trading_buyout_price);
			// 430
			BuildFieldsNew.get("trading_left_time").write(writeBuf, vo.trading_price);
			// 32
			BuildFieldsNew.get("level").write(writeBuf, vo.skill);
			// 44
			BuildFieldsNew.get("polar").write(writeBuf, vo.metal);
			// 40
			BuildFieldsNew.get("icon").write(writeBuf, vo.type);
			// 432
			BuildFieldsNew.get("trading_org_price").write(writeBuf, vo.trading_cg_price_ti);
			// 1
			BuildFieldsNew.get("name").write(writeBuf, vo.str);
			// 305
			BuildFieldsNew.get("gid").write(writeBuf, vo.iid_str);
			// 438
			BuildFieldsNew.get("trading_buyout_price").write(writeBuf, vo.dan_datastate);
			// 434
			BuildFieldsNew.get("trading_cg_price_ct").write(writeBuf, vo.char_online_state);
			// 431
			BuildFieldsNew.get("trading_price").write(writeBuf, vo.trading_org_price);
			// 436
			BuildFieldsNew.get("trading_sell_buy_type").write(writeBuf, vo.trading_appointee_name);
			// 这段代码决定是否进入新手战斗
			GameWriteTool.writeInt(writeBuf, vo.last_login_time);
			GameWriteTool.writeString(writeBuf, "");
		}
		GameWriteTool.writeInt(writeBuf, vo_61537_0.openServerTime);
		GameWriteTool.writeByte(writeBuf, vo_61537_0.account_online);
	}

	@Override
	public int cmd() {
		return 61537;
	}
}
