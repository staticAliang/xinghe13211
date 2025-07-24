package com.fengshen.server.process.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.CharaNickname;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45072_0;
import com.fengshen.server.data.write.M45072_0;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 随机生成角色名
 * 
 * 
 *
 */
@Service
public class CMD_RANDOM_NAME implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int gender = GameReadTool.readByte(buff);
		Vo_45072_0 vo_45072_0 = new Vo_45072_0();
		CharaNickname cn = new CharaNickname();
		cn.setSex(gender == 0 ? "男" : "女");
		// 查询出所有角色的名字
		List<Characters> findByObjSelectProperties = GameData.that.baseCharactersService.findByObjSelectProperties(null,
				"name");
		StringBuilder sb = new StringBuilder();
		for (Characters c : findByObjSelectProperties) {
			sb.append(c.getName()).append(",");
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		cn.setName(sb.toString());
		CharaNickname randomData = GameData.that.charaNicknameService.randomData(cn);
		vo_45072_0.new_name = randomData.getName();
		ByteBuf write = new M45072_0().write(vo_45072_0);
		ctx.writeAndFlush(write);
	}

	@Override
	public int cmd() {
		return 45073;
	}
}
