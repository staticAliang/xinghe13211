package com.fengshen.web.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.util.GameConfig;

@Controller
@RequestMapping("/sys/")
public class IndexController {
	
	@Value("${server.servlet.context-path}")
	private String path;

	@RequestMapping("/login.html")
	public String loginHtml(HttpSession session) {
		//缓存自定义名称
		session.setAttribute("gameName", GameConfig.config.getBaseConfig().getGameName());
		session.setAttribute("path", path);
		session.setAttribute("gameVersion", GameCommonUtil.gameVersion);
		return "sys/login.html";
	}
	
	@RequestMapping("/jump")
	public String jumpPage(String page) {
		page = page.substring(0, page.indexOf("."));
		page = page.replaceFirst("/", "");
		return "sys/"+page;
	}

	@RequestMapping("/charaPage")
	public String charaPage(String type) {
		return "sys/chara/chara_list.html";
	}
	@RequestMapping("/partyMember")
	public String charaPage() {
		return "sys/chara/partyMembers.html";
	}
	@RequestMapping("/changePassword")
	public String find_password(String userName) {
		return "vip4/mobile/sdk/find_password.html";
	}
	
	
	
	@RequestMapping("/index.html")
	public String indexHtml() {
		return "sys/index.html";
	}

	/**
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/user/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "sys/login.html";
	}
	
	/**
	 * 批量导出角色信息
	 * @param response
	 * @param name
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/wd/exportCharas")
	public void exportCharas(HttpServletResponse response, String name) throws UnsupportedEncodingException {
		StringBuilder sqls = new StringBuilder();
		for(String n:name.split("、")) {
			Characters chara = GameData.that.baseCharactersService.findOneBlobByName(n);
			if(chara == null) {
				continue;
			}
			StringBuilder sql = new StringBuilder();
			sql.append("#--------").append(n).append("\n");
			sql.append("INSERT INTO `characters`(`id`, `polar`, `name`, `sex`, `charge_score`, `map_id`, `map_name`, `x`, `y`, `level`, `gold_coin`, `portrait`, `month_tao`, `data`, `account_id`, `add_time`, `update_time`, `deleted`, `gid`, `online`, `cangku`, `texiao`, `genchong`, `backpack`, `pet_store`, `listshouhu`, `shizhuang`, `card_store`, `custom_shizhuang`, `tyzq_store`, `last_login_time`, `block`, `shut`, `xiaozi`, `fixed_team_name`, `last_login_ip`) VALUES (");
			
			sql.append("null");//id;
			sql.append(",");
			sql.append(chara.getPolar());sql.append(",");
			
			sql.append("'");
			sql.append(chara.getName());
			sql.append("'");
			sql.append(",");
			
			sql.append(chara.getSex());sql.append(",");
			sql.append(chara.getChargeScore());sql.append(",");
			
			sql.append(chara.getMapId());sql.append(",");
			
			sql.append("'");
			sql.append(chara.getMapName());
			sql.append("'");
			sql.append(",");
			
			sql.append(chara.getX());sql.append(",");
			sql.append(chara.getY());sql.append(",");
			sql.append(chara.getLevel());sql.append(",");
			sql.append(chara.getGoldCoin());sql.append(",");
			sql.append(chara.getPortrait());sql.append(",");
			sql.append(0);sql.append(",");
			
			
			sql.append("'");
			sql.append(chara.getData().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append(chara.getAccountId());sql.append(",");
			sql.append("NOW()");sql.append(",");
			sql.append("null");sql.append(",");
			sql.append(chara.getDeleted()==false?0:1);sql.append(",");
			
			
			sql.append("'");
			sql.append(chara.getGid());
			sql.append("'");
			sql.append(",");
			
			sql.append(chara.getOnline());
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getCangku().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getTexiao().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getGenchong().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getBackpack().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getPetStore().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			
			sql.append("'");
			sql.append(chara.getListshouhu().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			
			sql.append("'");
			sql.append(chara.getShizhuang().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getCardStore().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getCustomShizhuang().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getTyzqStore().replace("\"", "\\\""));
			sql.append("'");
			sql.append(",");
			
			sql.append(0);
			sql.append(",");
			
			
			sql.append(chara.getBlock());
			sql.append(",");
			
			
			sql.append(chara.getShut());
			sql.append(",");
			
			sql.append(chara.getXiaozi());
			sql.append(",");
			
			sql.append("'");
			sql.append(chara.getFixedTeamName());
			sql.append("'");
			sql.append(",");
			
			
			sql.append("'");
			sql.append(chara.getLastLoginIp());
			sql.append("'");
			sql.append(");\n");
			//查询这个玩家所有宠物
			List<CharaPet> petsByCid = GameData.that.charaPetService.getPetsByCid(chara.getId());
			for(CharaPet pet:petsByCid) {
				sql.append("INSERT INTO `chara_pet`(`id`, `uuid`, `cid`, `pet`, `pet_name`, `owner_name`, `add_time`, `update_time`) VALUES (");
				sql.append("null");
				sql.append(",");
				
				sql.append("'");
				sql.append(pet.getUuid());
				sql.append("'");
				sql.append(",");
				
				sql.append("'");
				sql.append(pet.getCid());
				sql.append("'");
				sql.append(",");
				
				sql.append("'");
				sql.append(pet.getPet().replace("\"", "\\\""));
				sql.append("'");
				sql.append(",");
				
				sql.append("'");
				sql.append(pet.getPetName());
				sql.append("'");
				sql.append(",");
				
				sql.append("'");
				sql.append(pet.getOwnerName());
				sql.append("'");
				sql.append(",");
				
				sql.append("NOW()");
				sql.append(",");
				sql.append("null");
				sql.append(");\n");
			}
			sqls.append(sql);
			sqls.append("\n");
		}
		response.setContentType("text/plain");
        response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("角色导出_"+System.currentTimeMillis()+".sql","UTF-8"));
        BufferedOutputStream buff = null; 
        ServletOutputStream outSTr = null; 
        try {
            outSTr = response.getOutputStream();
            buff = new BufferedOutputStream(outSTr); 
            buff.write(sqls.toString().getBytes("UTF-8")); 
            buff.flush(); 
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 导出
	 * @param uid
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/index/wd/exportJson")
	public void exportJson(String uid, HttpServletResponse response) throws UnsupportedEncodingException {
		FightContainer fightContainer = FightManager.getFightContainerByUid(uid);
		if(fightContainer == null) {
			ResponseView.fail("战斗不存在或已结束");
		}
		StringBuilder msg = new StringBuilder();
		msg.append("gameVersion:").append(GameCommonUtil.gameVersion).append("\nfightCharasA:").
		append(JSONObject.toJSONString(fightContainer.fightCharasA)).append("\nfightCharasB:").append(JSONObject.toJSONString(fightContainer.fightCharasB))
		.append("\nfightRecords:").append(JSONObject.toJSONString(fightContainer.fightRecords));
		
		response.setContentType("text/plain");
        response.addHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("战斗数据_"+uid+"_"+System.currentTimeMillis()+".txt","UTF-8"));
        BufferedOutputStream buff = null; 
        ServletOutputStream outSTr = null; 
        try {
            outSTr = response.getOutputStream();
            buff = new BufferedOutputStream(outSTr); 
            buff.write(msg.toString().getBytes("UTF-8")); 
            buff.flush(); 
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
