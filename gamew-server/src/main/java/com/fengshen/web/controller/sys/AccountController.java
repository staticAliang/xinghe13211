package com.fengshen.web.controller.sys;

import java.util.List;

import com.fengshen.db.domain.Charge;
import com.fengshen.db.domain.sys.SysUser;
import jodd.util.CollectionUtil;
import jodd.util.StringUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.vo.user.Vo_OTHER_LOGIN;
import com.fengshen.server.data.write.user.MSG_OTHER_LOGIN;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/account")
public class AccountController extends BaseController{

	
	/**
	 * 获取玩家所有账号
	 * @param pageInfo
	 * @return
	 */
	@PostMapping("/getAccounts")
	public ResponseView getAccounts(Page<Accounts> page,String name, Integer privilege) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		PageInfo<Accounts> pageInfo = new PageInfo<>(GameData.that.baseAccountsService.findAllManage(name,privilege));
		return ResponseView.ok(settingsPageFilter(pageInfo,"password","keyword"));
	}
	/**
	 * 更新用户权限
	 * @param id
	 * @param privilege
	 * @return
	 */
	@PostMapping("/updatePrivilege")
	public ResponseView updatePrivilege(Integer id, String privilege) {
		if(id == null) {
			ResponseView.fail("id不能为空");
		}
		Accounts accounts = new Accounts();
		accounts.setId(id);
		accounts.setPrivilege(Integer.valueOf(privilege));
		GameData.that.baseAccountsService.updateById(accounts);
		return ResponseView.ok();
	}
	/**
	 * 根据id获取账号信息
	 * @param id
	 * @return
	 */
	@PostMapping("/getAccountName")
	public ResponseView getAccountName(Integer id) {
		if(id == null) {
			ResponseView.fail("id不能为空");
		}
		Accounts findById = GameData.that.baseAccountsService.findById(id);
		String accountname = "";
		if(findById != null) {
			accountname = findById.getName();
		}
		return ResponseView.ok(accountname);
	}
	/**
	 * 封号和解封
	 * @param id
	 * @param state
	 * @return
	 */
	@PostMapping("/accountDisableAndEnable")
	public ResponseView accountDisableAndEnable(int id, boolean state) {
		Accounts accounts = new Accounts();
		accounts.setId(id);
		accounts.setDeleted(state);
		GameData.that.baseAccountsService.updateById(accounts);
		//获取到当前账号下所有角色并进行封闭
		Characters where = new Characters();
		where.setAccountId(id);
		List<Characters> findByAccountId = GameData.that.characterService.findByObjSelectProperties(where,"id","gid");
		for(Characters c:findByAccountId) {
			if(state) {
				if(GameObjectCharMng.getGameObjectChar(c.getId()) != null) {
					GameObjectChar game = GameObjectCharMng.getGameObjectChar(c.getId());
					Vo_OTHER_LOGIN login = new Vo_OTHER_LOGIN();
					login.setCode(0);
					login.setResult(2);
					login.setMsg("对不起您违反了游戏的公平,角色已被封。");
	            	GameObjectCharMng.getGameObjectChar(game.chara.id).sendOne(new MSG_OTHER_LOGIN(), login);
	            	GameObjectCharMng.getGameObjectChar(game.chara.id).offline();
	                GameObjectCharMng.getGameObjectCharList().remove(GameObjectCharMng.getGameObjectChar(game.chara.id));
				}
			}
			c.setDeleted(state);
			c.setOnline(0);
			GameData.that.baseCharactersService.updateById(c);
		}
		return ResponseView.ok();
	}
	
	@PostMapping({"/chargeMoney"})
    public ResponseView chargeMoney(String name,Integer money,String remark,final HttpSession session) {
        final SysUser loginUser = (SysUser) session.getAttribute("user");
        if (loginUser.getUserType() != 0) {
//            return ResponseView.unauthorized("无权操作");
        }
        if (money == null || money < 1) {
            return ResponseView.fail("money不能为空");
        }


		if (StringUtil.isAllEmpty(name, remark)) {
            return ResponseView.fail("name, remark不能为空");
        }
        if (!"全部账号".equals(name)) {
            Accounts oneByName = GameData.that.baseAccountsService.findOneByName(name);
            if (oneByName == null) {
                return ResponseView.fail("账号不存在");
            }
            Charge charge = new Charge();
            charge.setAccountname(oneByName.getName());
            charge.setCoin(money);
            charge.setMoney(money);
            charge.setAccountname(name);

            charge.setCode(oneByName.getRegisterCode());
            charge.setState(0);
            charge.type = 1;
            charge.remark = remark;
            GameData.that.baseChargeService.add(charge);
        }else {
            List<Accounts> all = GameData.that.baseAccountsService.findAll();
            if (all.size() <= 0) {
                return ResponseView.fail("没有一个账号");
            }
            for (Accounts accounts : all) {
                Charge charge = new Charge();
                charge.setAccountname(accounts.getName());
                charge.setCoin(money);
                charge.setMoney(money);
                charge.setAccountname(name);

                charge.setCode(accounts.getRegisterCode());
                charge.setState(0);
                charge.type = 2;
                charge.remark = remark;
                GameData.that.baseChargeService.add(charge);
            }
        }
        return ResponseView.ok();
    }
}
