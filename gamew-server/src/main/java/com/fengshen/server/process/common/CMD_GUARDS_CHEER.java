package com.fengshen.server.process.common;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.M12016_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.ShouHu;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置守护状态
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_GUARDS_CHEER implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int guard_id = GameReadTool.readInt(buff);
		int cheer = GameReadTool.readByte(buff);
		log.info("设置守护状态, guard_id={},cheer={}",guard_id,cheer);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		for (int i = 0; i < chara.listshouhu.size(); ++i) {
			if (guard_id == chara.listshouhu.get(i).id) {
				if (cheer == 1) {
					if (chara.canzhanshouhunumber == 0) {
						++chara.canzhanshouhunumber;
						chara.listshouhu.get(i).listShouHuShuXing.get(0).salary = 5;
					} else {
						chara.listshouhu.get(i).listShouHuShuXing.get(0).salary = chara.canzhanshouhunumber;
						++chara.canzhanshouhunumber;
					}
				}
				if (cheer == 0) {
					chara.listshouhu.get(i).listShouHuShuXing.get(0).salary = 0;
					--chara.canzhanshouhunumber;
				}
				chara.listshouhu.get(i).listShouHuShuXing.get(0).nil = cheer;
				List<ShouHu> list = new LinkedList<ShouHu>();
				list.add(chara.listshouhu.get(i));
				GameObjectChar.send(new M12016_0(), list, chara.id);
				GameUtil.closeDlg("GuardAttribDlg");
				GameUtil.openDlg("GuardAttribDlg");
			}
		}
	}

	@Override
	public int cmd() {
		return 4347;
	}
}
