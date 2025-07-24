package com.fengshen.web.controller.sys.chara;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.ChangeCard;
import com.fengshen.db.service.chara.ChangeCardService;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.web.controller.BaseController;

/**
 * 变身卡
 * 
 *
 */
@RestController
@RequestMapping("/wd/changeCard")
public class ChangeCardController extends BaseController {

	@Autowired
	private ChangeCardService changeCardService;
	
	@PostMapping("/getChangeCards")
	public ResponseView getChangeCards() {
		return ResponseView.ok(changeCardService.selectAll());
	}
	
	@PostMapping("/updateChangeCard")
	public ResponseView updateChangeCard(ChangeCard changeCard) {
		changeCardService.updateByPrimaryKeySelective(changeCard);
		return ResponseView.ok();
	}
	
	@PostMapping("/addChangeCard")
	public ResponseView addChangeCard(ChangeCard changeCard) {
		if(changeCard.getTime() == null) {
			return ResponseView.fail("时间不能为空");
		}
		changeCard.setAddTime(new Date());
		changeCardService.insertSelective(changeCard);
		for(ChangeCard c:GameData.that.changeCardService.selectAll()) {
			GameCore.changeCardMap.put(c.getName(), c);
		}
		return ResponseView.ok();
	}
	
	@PostMapping("/delChangeCard")
	public ResponseView delChangeCard(int id) {
		ChangeCard card = new ChangeCard();
		card.setId(id);
		changeCardService.deleteByPrimaryKey(card);
		for(ChangeCard c:GameData.that.changeCardService.selectAll()) {
			GameCore.changeCardMap.put(c.getName(), c);
		}
		return ResponseView.ok();
	}
}
