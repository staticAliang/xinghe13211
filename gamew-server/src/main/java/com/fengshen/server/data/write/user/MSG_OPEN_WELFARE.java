package com.fengshen.server.data.write.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_OPEN_WELFARE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_OPEN_WELFARE extends BaseWrite<Vo_OPEN_WELFARE> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_OPEN_WELFARE object2) {
		
		GameWriteTool.writeInt(writeBuf, object2.leftTime);
		
		GameWriteTool.writeByte(writeBuf, object2.times);
		
		GameWriteTool.writeByte(writeBuf, object2.leftTimes);
		
		GameWriteTool.writeByte(writeBuf, object2.isCanSign);
		
		GameWriteTool.writeByte(writeBuf, object2.isCanGetNewPalyerGift);
		
		GameWriteTool.writeByte(writeBuf, object2.firstChargeState);
		
		GameWriteTool.writeByte(writeBuf, object2.cumulativeReward);
		
		GameWriteTool.writeByte(writeBuf, object2.loginGiftState);
		
		GameWriteTool.writeByte(writeBuf, object2.activeCount);
		
		GameWriteTool.writeByte(writeBuf, object2.holidayCount);
		
		GameWriteTool.writeByte(writeBuf, object2.isCanReplenishSign);
		
		GameWriteTool.writeByte(writeBuf, object2.isShowHuiGui);
		
		GameWriteTool.writeByte(writeBuf, object2.canGetZXQYHuoYue);
		
		GameWriteTool.writeByte(writeBuf, object2.canGetZXQYSevenLogin);
		
		GameWriteTool.writeByte(writeBuf, object2.canGetActive2020);
		
		GameWriteTool.writeByte(writeBuf, object2.returnHelpFlag);
		
		GameWriteTool.writeByte(writeBuf, object2.isShowZhaohui);
		GameWriteTool.writeByte(writeBuf, object2.activeVIPFlag);
		GameWriteTool.writeByte(writeBuf, object2.rename_discount_time);
		GameWriteTool.writeByte(writeBuf, object2.summerSF2017);
		GameWriteTool.writeByte(writeBuf, object2.zaohua);
		GameWriteTool.writeByte(writeBuf, object2.welcomeDrawStatue);
		GameWriteTool.writeByte(writeBuf, object2.activeLoginStatue);
		GameWriteTool.writeByte(writeBuf, object2.xundcf);
		GameWriteTool.writeByte(writeBuf, object2.mergeLoginStatus);
		GameWriteTool.writeByte(writeBuf, object2.mergeLoginActiveStatus);
		GameWriteTool.writeByte(writeBuf, object2.reentryAsktaoRecharge);
		GameWriteTool.writeByte(writeBuf, object2.expStoreStatus);
		
		GameWriteTool.writeByte(writeBuf, object2.isShowXYFL);
		GameWriteTool.writeByte(writeBuf, object2.isShowXFSD);
		GameWriteTool.writeByte(writeBuf, object2.isShowNCJF);
		GameWriteTool.writeByte(writeBuf, object2.qmpkDrawTimes);
		GameWriteTool.writeByte(writeBuf, object2.isDifuPoint);
		GameWriteTool.writeByte(writeBuf, object2.double_lottery);
		GameWriteTool.writeByte(writeBuf, object2.new_year_bless_flag);
		GameWriteTool.writeByte(writeBuf, object2.isShowYLMB);
		GameWriteTool.writeByte(writeBuf, object2.fixed_team_welfare_flag);
		//new_dist_onlinemall_flag
		GameWriteTool.writeByte(writeBuf, -1);
		//isShowBaiBaoDai
		GameWriteTool.writeByte(writeBuf, -1);
		//fixed_team_effort_flag
		GameWriteTool.writeByte(writeBuf, -1);
		//caiShenZengLi
		GameWriteTool.writeByte(writeBuf, -1);
		//lantern2021_yxzf
		GameWriteTool.writeByte(writeBuf, -1);
		//lantern2021_tyyh
		GameWriteTool.writeByte(writeBuf, -1);
		//lingx_keepsake
		GameWriteTool.writeByte(writeBuf, -1);
		//newServeAddNum
		GameWriteTool.writeShort(writeBuf, -1);
		GameWriteTool.writeInt(writeBuf, 0);
		GameWriteTool.writeInt(writeBuf, 1000);
	}

	@Override
	public int cmd() {
		return 49159;
	}
}
