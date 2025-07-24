package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_65527_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M65527_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final ListVo_65527_0 listVo_65527_0 = (ListVo_65527_0) object;
		GameWriteTool.writeInt(writeBuf, listVo_65527_0.id);
		final Vo_65527_0 vo = listVo_65527_0.vo_65527_0;
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = UtilObjMap.Vo_65527_0(vo);
		for (final Map.Entry<Object, Object> entry : map.entrySet()) {
			if (entry.getValue() instanceof Integer) {
				if (entry.getValue().equals(0) && !entry.getKey().equals("balance") && !entry.getKey().equals("gender")
						&& !entry.getKey().equals("resist_metal") && !entry.getKey().equals("wood")
						&& !entry.getKey().equals("water") && !entry.getKey().equals("fire")
						&& !entry.getKey().equals("earth") && !entry.getKey().equals("stamina")
						&& !entry.getKey().equals("marriage_book_id")) {
					continue;
				}
				continue;
			} else if (entry.getValue().equals("")) {
			}
		}
		GameWriteTool.writeShort(writeBuf, map.size());
		for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
			if (BuildFields.data.get(entry2.getKey()) != null) {
				BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
			} else {
				System.out.println(entry2.getKey());
			}
		}
	}

	@Override
	public int cmd() {
		return 65527;
	}
}
