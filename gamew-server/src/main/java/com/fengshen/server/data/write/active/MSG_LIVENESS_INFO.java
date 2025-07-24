package com.fengshen.server.data.write.active;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.active.Vo_LIVENESS_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_LIVENESS_INFO extends BaseWrite<Object>{
	
	@SuppressWarnings("unchecked")
	@Override
	protected void writeO(ByteBuf buff, Object object) {
		Object[] obj = (Object[]) object;
		List<Vo_LIVENESS_INFO> infos = (List<Vo_LIVENESS_INFO>) obj[0];
		GameWriteTool.writeShort(buff, infos.size());
		for(Vo_LIVENESS_INFO v:infos) {
			GameWriteTool.writeString(buff, v.getName());
			GameWriteTool.writeShort(buff, v.getCount());
			GameWriteTool.writeShort(buff, v.getActiveValue());
			GameWriteTool.writeString(buff,"");
		}
		//底板完成任务计数
		
		List<Integer[]> datas = (List<Integer[]>) obj[1];
		//activityRewardCount
		GameWriteTool.writeByte(buff, datas.size());
		for(Integer[] i:datas) {
			//activity
			GameWriteTool.writeShort(buff, i[0]);
			//status
			GameWriteTool.writeByte(buff, i[1]);
		}
		
		GameWriteTool.writeByte(buff, 0);
	}

	@Override
	public int cmd() {
		return 0x9013;
	}

}
