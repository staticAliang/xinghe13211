package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_EQUIP_CARD extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		Object[] o = (Object[]) object;
		Goods goods = (Goods) o[0];
        GameWriteTool.writeByte(buff, goods.pos);
        GameWriteTool.writeShort(buff, 10);//count
        Map<Object, Object> map = new HashMap<Object, Object>();
        
        if (goods.goodsInfo != null) {
            map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
            GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0) && entry.getKey().equals("silver_coin")) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(buff, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String)entry2.getKey()).write(buff, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
        GameCommonUtil.goodsCreate(buff, goods);
	}

	@Override
	public int cmd() {
		return 0xB017;
	}

}
