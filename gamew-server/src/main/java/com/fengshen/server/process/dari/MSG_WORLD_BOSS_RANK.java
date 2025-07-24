package com.fengshen.server.process.dari;

import com.fengshen.db.domain.CreepsStore;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 动态数据
 * 
 * 
 *
 */
@Slf4j
@Service
public class MSG_WORLD_BOSS_RANK extends BaseWrite {
	@Override
	protected void writeO(ByteBuf buff, Object object) {
		List<rank_role> list = (List<rank_role>)object;
		GameWriteTool.writeShort(buff, list.size());
		for (int i = 0; i < list.size(); i++) {
			GameWriteTool.writeShort(buff, (int) list.get(i).getRank());
			GameWriteTool.writeString(buff, list.get(i).getName());
			GameWriteTool.writeInt(buff, list.get(i).getDamage());
		}
	}

	@Override
	public int cmd() {
		return 33009;
	}

}
