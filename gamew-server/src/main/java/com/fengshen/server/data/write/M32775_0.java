package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M32775_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Goods goods = (Goods)object;
        GameWriteTool.writeByte(writeBuf, goods.pos);
        GameWriteTool.writeByte(writeBuf, 3);
        GameWriteTool.writeShort(writeBuf, 10);
        Map<Object, Object> map = new HashMap<Object, Object>();
        if (goods.goodsInfo != null) {
            map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupType);
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry : map.entrySet()) {
                if (BuildFields.data.get(entry.getKey()) != null) {
                    BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
                }
                else {
                    System.out.println(entry.getKey());
                }
            }
        }
        if (goods.goodsBasics != null) {
            map = UtilObjMapshuxing.GoodsBasics(goods.goodsBasics);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsBasics.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsBasics.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0)) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
        if (goods.goodsLanSe != null) {
            map = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsLanSe.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsLanSe.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0)) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
        if (goods.goodsGaiZao != null) {
            map = UtilObjMapshuxing.GoodsGaiZao(goods.goodsGaiZao);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsGaiZao.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsGaiZao.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0)) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
        if (goods.goodsGaiZaoGongMing != null) {
            map = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMing.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMing.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0)) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
        if (goods.goodsGaiZaoGongMingChengGong != null) {
            map = UtilObjMapshuxing.GoodsGaiZaoGongMingChengGong(goods.goodsGaiZaoGongMingChengGong);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMingChengGong.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMingChengGong.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0)) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
        if (goods.goodsLvSeGongMing != null) {
            map = UtilObjMapshuxing.GoodsLvSeGongMing(goods.goodsLvSeGongMing);
            map.remove("groupNo");
            map.remove("groupType");
            GameWriteTool.writeByte(writeBuf, goods.goodsLvSeGongMing.groupNo);
            GameWriteTool.writeByte(writeBuf, goods.goodsLvSeGongMing.groupType);
            final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                final Map.Entry<Object, Object> entry = it.next();
                if (entry.getValue().equals(0)) {
                    it.remove();
                }
            }
            GameWriteTool.writeShort(writeBuf, map.size());
            for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
                if (BuildFields.data.get(entry2.getKey()) != null) {
                    BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
                }
                else {
                    System.out.println(entry2.getKey());
                }
            }
        }
    }
    
    @Override
    public int cmd() {
        return 32775;
    }
}
