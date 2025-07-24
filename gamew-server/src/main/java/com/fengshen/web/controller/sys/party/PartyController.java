package com.fengshen.web.controller.sys.party;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.service.party.PartyMemberService;
import com.fengshen.db.service.party.PartyService;
import com.fengshen.server.game.GameCore;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 帮派管理器
 * 
 * 
 *
 */
@Controller
@RequestMapping("/party")
public class PartyController extends BaseController {

	@Autowired
	private PartyService ps;
	@Autowired
	private PartyMemberService pms;

	/**
	 * 获取所有帮派
	 * 
	 * @param page
	 * @param party
	 * @return
	 */
	@PostMapping("/getPartys")
	@ResponseBody
	public ResponseView getPartys(Page<Party> page, Party party) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		PageInfo<Party> pageInfo = new PageInfo<>(ps.select(party));
		return ResponseView.ok(settingsPageFilter(pageInfo, "reviewIconMd5"));
	}

	/**
	 * 获取某个帮派的成员
	 * 
	 * @param page
	 * @param partyMember
	 * @return
	 */
	@PostMapping("/getPartyMembers")
	@ResponseBody
	public ResponseView getPartyMembers(PageInfo<PartyMember> page, PartyMember partyMember) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		PageInfo<PartyMember> pageInfo = new PageInfo<>(pms.select(partyMember));
		return ResponseView.ok(settingsPageFilter(pageInfo, "reviewIconMd5"));
	}

	/**
	 * 更新帮派信息
	 * 
	 * @param party
	 * @return
	 */
	@PostMapping("/updateParty")
	@ResponseBody
	public ResponseView updateParty(Party party) {
		if (party.getPartyId() == null)
			ResponseView.fail("帮派id不能为空");
		Party p = ps.findByPartyId(party.getPartyId());
		if(party.getHeir()!= null && party.getHeir().equals("clearIcon")) {
			//清除图标
			p.setIconMd5("");
			p.setReviewIconMd5(null);
			ps.updateByPrimaryKey(p);
		}else {
			Example example = new Example(Party.class);
			example.createCriteria().andEqualTo("partyId", party.getPartyId());
			ps.updateByExampleSelective(party, example);
		}
		GameCore.partyMap.put(p.getPartyName(), ps.findByPartyId(party.getPartyId()));
		return ResponseView.ok();
	}

	/**
	 * 获取帮派图标
	 * 
	 * @param partyId
	 * @throws IOException
	 */
	@RequestMapping("/getPartyIcon")
	public void getPartyIcon(String partyId, HttpServletResponse response) throws IOException {
		if (partyId == null)
			ResponseView.fail("帮派id不能为空");

		Party find = ps.findByPartyId(partyId);
		byte[] data = find.getReviewIconMd5();
		if (data != null) {
			response.setContentType("image/jpeg");
			response.setCharacterEncoding("UTF-8");
			OutputStream outputSream = response.getOutputStream();
			outputSream.write(data);
			outputSream.flush();
		}
	}
	
	/**
	 * 帮派操作
	 * @param data
	 * @return
	 */
	@RequestMapping("/partyOperation")
	@ResponseBody
	public ResponseView partyOperation(Map<String,String> data) {
		//发送公告
		if(data.get("type").equals("publiMsg")) {
			//帮派id
		}
		return ResponseView.ok();
	}
}
