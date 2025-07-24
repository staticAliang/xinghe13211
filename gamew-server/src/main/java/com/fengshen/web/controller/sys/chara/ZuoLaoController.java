package com.fengshen.web.controller.sys.chara;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.server.data.vo.zuolao.Vo_ZUOLAO_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;

/**
 * 坐牢人员信息
 * @author aaa
 *
 */
@RequestMapping("/zuolao")
@RestController
public class ZuoLaoController extends BaseController {
	/**
	 * 获取坐牢人员信息
	 * @param name 姓名
	 * @return
	 */
	@PostMapping("/getZuoLaoInfo")
	public ResponseView getZuoLaoInfo(String name) {
		List<Vo_ZUOLAO_INFO> infos = new ArrayList<>();
		for(GameObjectChar all:GameObjectCharMng.getAll()) {
			if(all.chara.crimeTime>0 && all.chara.taskMap.get("坐牢") != null) {
				Vo_ZUOLAO_INFO vo_ZUOLAO_INFO = new Vo_ZUOLAO_INFO(all.chara);
				vo_ZUOLAO_INFO.setServerName(GameConfig.lineName + all.chara.line + "线");
				infos.add(vo_ZUOLAO_INFO);
			}
		}
		return ResponseView.ok(infos);
	}
	
	/**
	 * 释放某个坐牢人员
	 * @param name
	 * @return
	 */
	@PostMapping("/zuoLaoRelease")
	public ResponseView zuoLaoRelease(String name) {
		GameObjectChar gameObjectChar = getGameObjectChar(name);
		Chara chara = gameObjectChar.chara;
		if(chara.mapName.equals("监狱") && chara.taskMap.get("坐牢") != null)  {
			chara.x = 26;
			chara.y = 30;
			GameLine.getGameMap(chara.line, "监狱").join(gameObjectChar);
			GameCommonUtil.sendTips("你被GM释放了", gameObjectChar);
		}
		chara.crimeTime = 0;
		//释放该犯人
		chara.isNameRed = 0;
		//删除任务
		GameUtilRenWu.removeTask("坐牢", chara);
		return ResponseView.ok();
	}
}