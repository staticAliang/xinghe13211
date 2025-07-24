package com.fengshen.web.controller.sys.shidao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.DateUtil;
import com.fengshen.core.util.ResponseView;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.ShiDao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameShiDao.ShiDaoRank;
import com.fengshen.server.game.GameZone;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;

/**
 * 试道
 * 
 *
 */
@RequestMapping("/sys/wd/shidao")
@RestController
public class WdShiaDaoController extends BaseController{

	/**
	 * 获取试道相关信息
	 * @return
	 */
	@PostMapping("/getShiDaoInfo")
	public ResponseView getShiDaoInfo(String type, @RequestParam Map<String,Object> data) {
		if(type == null) {
			ResponseView.fail("type为空");
		}
		Object o = null;
		if("shidaoYuanMoInfo".equals(type)) {
			Map<Integer,Integer> map = new HashMap<>();
			//获取试道元魔信息
			for(int i:GameShiDao.shidaolevel) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(i);
				int size = 0;
				if(gameZone != null && !gameZone.isEmpty()) {
					for(GameMap gameMap:gameZone) {
						size+=gameMap.gameShiDao.shidaoyuanmo.size();
					}
				}
				map.put(i, size);
			}
			o = map;
		}else if("shidaoCharaInfo".equals(type)) {
			//试道人员情况
			Map<Integer,Integer> map = new HashMap<>();
			//获取试道人员信息
			for(int i:GameShiDao.shidaolevel) {
				List<GameObjectChar> session = GameShiDao.getShiDaoMapSession(i);
				if(session != null) {
					map.put(i, session.size());
				}else {
					map.put(i, 0);
				}
			}
			o = map;
		}else if("shidaoYuanMoClear".equals(type)) {
			//清除某个阶段的试道元魔
			Integer object = Integer.valueOf((String)data.get("jieduan"));
			if(object != null) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(object);
				if(gameZone != null && !gameZone.isEmpty()) {
					for(GameMap gameMap:gameZone) {
						for (int j = 0; j < gameMap.gameShiDao.shidaoyuanmo.size(); ++j) {
							Vo_APPEAR v = gameMap.gameShiDao.shidaoyuanmo.get(j);
							v.isHide = 1;
							gameMap.send(new M65529_0(), v);
						}
						// 清除所有元魔
						gameMap.gameShiDao.shidaoyuanmo.clear();
					}
				}
			}
		}else if("refreshYuanMo".equals(type)) {
			Integer object = Integer.valueOf((String)data.get("jieduan"));
			if(object != null) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(object);
				if(gameZone != null && !gameZone.isEmpty()) {
					for(GameMap gameMap:gameZone) {
						if(gameMap.sessionList.size()>0 && gameMap.gameShiDao.shidaoyuanmo.size()<100) {
							GameShiDao.refreShiDaoYuanMo(gameMap.gameShiDao, gameMap);
						}
					}
				}
			}
		}else if("openProject".equals(type)) {
			//操作阶段关闭或者开启
			ShiDao shidao = GameConfig.config.getShidao();
			String object = (String) data.get("jieduan");
			Integer value = Integer.valueOf((String)data.get("value"));
			shidao.getOpenProject().put(object, value);
			flushConfig();
		}else if("shidaoCharaOut".equals(type)) {
			//踢出某个阶段的人员
			Integer object = Integer.valueOf((String)data.get("jieduan"));
			if(object != null) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(object);
				if(gameZone != null) {
					for(GameMap gameMap:gameZone) {
						List<GameObjectChar> sessionList = gameMap.sessionList;
						for(GameObjectChar g:sessionList) {
							Chara ch = g.chara;
							// 全部带回城里
							ch.x = 128;
							ch.y = 52;
							final Vo_20481_0 vo_20481_10 = new Vo_20481_0();
							vo_20481_10.msg = "你被系统强制踢出试道场";
							vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
							GameObjectChar.send(new M20481_0(), vo_20481_10, ch.id);
							GameLine.getGameMapname(ch.line, "天墉城").join(GameObjectCharMng.getGameObjectChar(ch.id));
						}
					}
				}
			}
		}
		return ResponseView.ok(o);
	}
	
	/**
	 * 获取试道各个阶段排行信息
	 * @return
	 */
	@PostMapping("/getRankInfo")
	public ResponseView getRankInfo() {
		//报名阶段
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date(GameShiDao.getStartTime()));
		cd.set(Calendar.MINUTE, cd.get(Calendar.MINUTE) + (int) GameShiDao.getJoinTime() / 1000 / 60);
		String startTime = DateUtil.format(new Date(cd.getTime().getTime()), "yyyy-MM-dd H:mm:ss");
		//元魔阶段
		String durationTime = DateUtil.format(new Date(GameCommonUtil.yuanmoStartTime + GameShiDao.getDurationTime()), "yyyy-MM-dd H:mm:ss");
		//巅峰对决阶段
		String pkTime = DateUtil.format(new Date(GameCommonUtil.pkStartTime+GameShiDao.getPkTime()), "yyyy-MM-dd H:mm:ss");
		//图表信息
		Map<String,Object> datas = new HashMap<>();
		//状态
		datas.put("status", GameShiDao.statzhuangtai);
		//元魔和PK阶段
		List<Map<String,Object>> series = new ArrayList<>();
		List<Integer> sortNum = new ArrayList<>();
		if(GameShiDao.statzhuangtai == 2 || GameShiDao.statzhuangtai == 3) {
			//数据信息
			Integer[] shidaolevel = GameShiDao.shidaolevel;
			for (int i = 0; i < shidaolevel.length; ++i) {
				Integer level = shidaolevel[i];
				if(level>=70 && level<=79) {
					continue;
				}
				// 获取该阶段排名信息
				List<ShiDaoRank> sortMap = GameShiDao.sortShiDao.get(level);
				Map<String,Object> serie = new HashMap<>();
				serie.put("name", GameShiDao.getShiDaoJieDuan(level));
				serie.put("type", "bar");
				List<Map<String,Object>> serieDatas = new ArrayList<>();
				if(sortMap != null) {
					int rankNum = 1;
					for(ShiDaoRank rank:sortMap) {
						Map<String,Object> serieData = new HashMap<>();
						serieData.put("name", rank.getTeamLeaderName());
						serieData.put("value", rank.getScore());
						sortNum.add(rank.getScore());
						serieDatas.add(serieData);
						if(rankNum == 4) {
							break;
						}
						rankNum++;
					}
					serie.put("data", serieDatas);
				}else {
					serie.put("data", new ArrayList<>());
				}
				series.add(serie);
			}
		}
		datas.put("series", series);
		datas.put("startTime", startTime);
		datas.put("durationTime", durationTime);
		datas.put("pkTime", pkTime);
		sortNum.sort((o1,o2)->o2.compareTo(o1));
		datas.put("sortNum", sortNum.isEmpty()?10:sortNum.get(0)+10);
		
		return ResponseView.ok(datas);
	}
}