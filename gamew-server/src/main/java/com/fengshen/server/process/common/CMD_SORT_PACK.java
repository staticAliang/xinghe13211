package com.fengshen.server.process.common;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_8249_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M8249_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 包裹排序
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SORT_PACK implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("整理背包");
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_8249_0 vo_8249_0 = new Vo_8249_0();
		vo_8249_0.start_range = 1;
		GameObjectChar.send(new M8249_0(), vo_8249_0);
		// 整理背包里面的数据
		int i = 41;
		chara.backpack.sort(new Comparator<Goods>() {
			@Override
			public int compare(Goods o1, Goods o2) {
				return o1.goodsInfo.str.compareTo(o2.goodsInfo.str);
			}
		});
		for (Goods pack : chara.backpack) {
			if (pack.pos >= 41 && pack.pos <= 165) {
				List<Goods> listbeibao = new ArrayList<Goods>();
				Goods removeGoods = new Goods();
				removeGoods.goodsBasics = null;
				removeGoods.goodsInfo = null;
				removeGoods.goodsLanSe = null;
				removeGoods.pos = pack.pos;
				listbeibao.add(removeGoods);
				// 删移除原来的
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(removeGoods));
				// 在添加到新的位置
				pack.pos = i;
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(pack));
				i++;
			}
		}
		GameObjectChar.send(new M65525_0(), chara.backpack);
		// 整理仓库
		int storeStart = 201;
		chara.cangku.sort(new Comparator<Goods>() {
			@Override
			public int compare(Goods o1, Goods o2) {
				return o1.goodsInfo.str.compareTo(o2.goodsInfo.str);
			}
		});
		for (Goods pack : chara.cangku) {
			if (pack.pos >= 201 && pack.pos <= 300) {
				// 在添加到新的位置
				pack.pos = storeStart;
				storeStart++;
			}
		}
		// 刷新仓库
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.list = chara.cangku;
		GameObjectChar.send(new M61677_0(), vo_61677_0);

		// 完成整理背包
		vo_8249_0 = new Vo_8249_0();
		vo_8249_0.start_range = 0;
		GameObjectChar.send(new M8249_0(), vo_8249_0);
	}

	@Override
	public int cmd() {
		return 8246;
	}
}