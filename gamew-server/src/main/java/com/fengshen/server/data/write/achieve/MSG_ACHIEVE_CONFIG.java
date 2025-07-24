package com.fengshen.server.data.write.achieve;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.achieve.Vo_ACHIEVE_CONFIG;
import com.fengshen.server.data.vo.achieve.Vo_ACHIEVE_CONFIG.Target;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a通知客户单成就配置
 * 
 *
 */
public class MSG_ACHIEVE_CONFIG extends BaseWrite<List<Vo_ACHIEVE_CONFIG>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_ACHIEVE_CONFIG> object) {
		
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_ACHIEVE_CONFIG config:object) {
			GameWriteTool.writeInt(buff, config.getAchieve_id());
			GameWriteTool.writeString(buff, config.getName());
			GameWriteTool.writeInt(buff, config.getPoint());
			GameWriteTool.writeInt(buff, config.getProgress());
			GameWriteTool.writeString(buff, config.getBonus_desc());
			GameWriteTool.writeString(buff, config.getAchieve_desc());
			GameWriteTool.writeShort(buff, config.getCategory());
			GameWriteTool.writeShort(buff, config.getOrder());
			if(config.getTargets() != null) {
				GameWriteTool.writeByte(buff, config.getTargets().size());
				for(Target target:config.getTargets()) {
					GameWriteTool.writeString(buff, target.getDes());
					GameWriteTool.writeShort(buff, target.getProcess());
				}
			}
		}
	} 

	@Override
	public int cmd() {
		return 0x80B5;
	}

}
