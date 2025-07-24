package com.fengshen.server.process.active;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_32825_0;
import com.fengshen.server.data.write.M32825_0;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

@Service
public class C32824_0 implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		List<Vo_32825_0> vos = new ArrayList<>();
		int endTime = (int) (System.currentTimeMillis()/1000L)+10000;
		vos.add(new Vo_32825_0("summer_day_2019_xzjs",1561582800,endTime));
		vos.add(new Vo_32825_0("qixi_2019_qqzy",1561582800,endTime));
		vos.add(new Vo_32825_0("huazhuang_wuhui",1561582800,endTime));
		vos.add(new Vo_32825_0("suiji_richange",1561582800,endTime));
		vos.add(new Vo_32825_0("tianjiangbaohe",1561582800,endTime));
		vos.add(new Vo_32825_0("huanlebaoxiang",1561582800,endTime));
		vos.add(new Vo_32825_0("new_year_attendance",1561582800,endTime));
		vos.add(new Vo_32825_0("qixi_2019_lmqg",1561582800,endTime));
		vos.add(new Vo_32825_0("summer_day_2019_sxdj",1561582800,endTime));
		vos.add(new Vo_32825_0("limit_purchase",1561582800,endTime));
		vos.add(new Vo_32825_0("summer_day_2019_smsz",1561582800,endTime));
		vos.add(new Vo_32825_0("summer_day_2019_sswg",1561582800,endTime));
		vos.add(new Vo_32825_0("yisheng_pengyou",1561582800,endTime));
		vos.add(new Vo_32825_0("reentry_asktao_2016",1561582800,endTime));
		vos.add(new Vo_32825_0("global_double",1561582800,endTime));
		vos.add(new Vo_32825_0("month_charge_gift",1561582800,endTime));
		vos.add(new Vo_32825_0("newdisthelp",1561582800,endTime));
		vos.add(new Vo_32825_0("good_voice",1561582800,endTime));
		vos.add(new Vo_32825_0("luobo_taozi_dashouji",1561582800,endTime));
		//持续15天
//		vos.add(new Vo_32825_0("world_redbag",1561582800,(int) (System.currentTimeMillis()/1000L)+1296000));
		GameObjectChar.send(new M32825_0(), vos);
	}

	@Override
	public int cmd() {
		return 32824;
	}
}
