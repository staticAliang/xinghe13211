package com.fengshen.web.controller.sys;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.DesUtil;
import com.fengshen.core.util.ErrorCode;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.sys.SysUser;
import com.fengshen.db.service.base.SysUserService;
import com.fengshen.server.game.GameData;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;
import com.qiniu.util.Md5;

import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@RequestMapping("/sys/user")
@RestController
@Slf4j
public class SysUserController extends BaseController{

	@Autowired
	private SysUserService s;
	
	/**
	 * 登录
	 * @param username
	 * @param password 
	 * @param session
	 * @return
	 */
	@PostMapping("/login")
	public ResponseView login(String username, String password, HttpSession session, HttpServletRequest request) {
		if(StringUtils.isNullOrEmpty(username)) {
			ResponseView.fail("用户名不能为空");
		}else if(StringUtils.isNullOrEmpty(password)) {
			ResponseView.fail("密码不能为空");
		}
		//限制ip
		String ip = getIP(request);
		if(GameData.that.redisUtils.get("REJECT_LOGIN_IP_"+ip) != null) {
			ResponseView.unauthorized("此ip被拒绝登录");
		}
		if(GameData.that.redisUtils.get("REJECT_LOGIN_"+username) != null) {
			ResponseView.unauthorized("拒绝登录");
		}
		String encrypt = DesUtil.encrypt(Md5.md5(password.getBytes()),"fswendao");
		SysUser login = s.login(username, encrypt);
		if(login == null) {
			//记录次数
			int errorCount = GameData.that.redisUtils.getIncr2("LOGIN_ERROR_COUNT_"+username);
			if(errorCount>=15 && errorCount<=20) {
				GameData.that.redisUtils.set("REJECT_LOGIN_"+username, username, 30, TimeUnit.MINUTES);
				GameData.that.redisUtils.set("REJECT_LOGIN_IP_"+ip, ip, 30, TimeUnit.MINUTES);
			}
			ResponseView.fail("用户名或密码错误");
		}
		//如果为0表示不需要指定ip授权
		if(!"0".equals(login.getAllowLoginIp())) {
			boolean isAllowLogin = false;
			if(login.getAllowLoginIp() != null) {
				String[] allowLoginIp = login.getAllowLoginIp().split(",");
				for(String ips:allowLoginIp) {
					if(ip.equals(ips)) {
						isAllowLogin = true;
					}
				}
			}
			if(!isAllowLogin) {
				log.error("用户名：{}，尝试登录但被系统拒绝了，目标ip地址：{}",username,ip);
				ResponseView.unauthorized("无权登录");
			}
		}
		session.setAttribute("user", login);
		//更新登录地址
		SysUser user = new SysUser();
		user.setId(login.getId());
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(ip);
		//更新信息
		s.updateByPrimaryKeySelective(user);
		//初始化相关配置
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andIn("uuid", Lists.newArrayList("c7608ca8c7af4a30b7f4f46e7ad47ad3","ct_config","sh_hunqiao"));
		List<ConfigInfo> selectByExample = GameData.that.configInfoService.selectByExample(example);
		for(ConfigInfo configInfo:selectByExample) {
			session.setAttribute(configInfo.getUuid(), 1);
		}
		GameData.that.redisUtils.delete("LOGIN_ERROR_COUNT_"+username);
		return ResponseView.ok(login);
	}
	
	/**
	 * 修改密码
	 * @param sue
	 * @return
	 */
	@PostMapping("/changePassword")
	public ResponseView changePassword(HttpSession session, String password, String newPassword) {
		SysUser user = (SysUser) session.getAttribute("user");
		SysUser su = s.getUserById(user.getId());
		if (password == null) 
			ResponseView.fail(ErrorCode.E10109);
		if (newPassword == null) 
			ResponseView.fail(ErrorCode.E10108);
		//判断输入的原密码是否正确
		String encrypt = DesUtil.encrypt(Md5.md5(password.getBytes()),"fswendao");
		if (!encrypt.equals(su.getPassword())) 
			ResponseView.fail(ErrorCode.E10107);
		
		SysUser newUser = new SysUser();
		newUser.setId(su.getId());
		newUser.setPassword(DesUtil.encrypt(Md5.md5(newPassword.getBytes()),"fswendao"));
		s.changePassword(newUser);
		return ResponseView.ok();
	}
	
	/**
	 * a获取系统用户
	 * @param page 分页
	 * @param username 用户名
 	 * @return
	 */
	@PostMapping("/getSysUsers")
	public ResponseView getSysUsers(Page<SysUser> page, String username) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("create_time desc");
		Example example = new Example(SysUser.class);
		if(!StringUtils.isNullOrEmpty(username) ) {
			example.createCriteria().andLike("userName", username);
		}
		List<SysUser> sysUsers = s.selectByExample(example);
		PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
		return ResponseView.ok(settingsPageFilter(pageInfo, "password","firstLoginUpdatePasswordFlag","createUserId","state","sex"));
	}
	
	/**
	 * a删除用户
	 * @param id 
	 * @return
	 */
	@PostMapping("/delSysUser")
	public ResponseView delSysUser(HttpSession session, int id) {
		SysUser user = s.selectByPrimaryKey(id);
		if(user == null ) {
			ResponseView.fail("不存在该用户");
		}
		//如果该用户不是超级用户
		SysUser loginUser = (SysUser) session.getAttribute("user");
		if(loginUser.getUserType() != 0) {
			ResponseView.unauthorized("无权操作");
		}
		//如果删除的对象是超级用户
		if(user.getUserType() == 0) {
			ResponseView.unauthorized("无权操作");
		}
		s.deleteByPrimaryKey(id);
		return ResponseView.ok();
	}
	
	/**
	 * a添加用户
	 * @param user 
	 * @return
	 */
	@PostMapping("/addSysUser")
	public ResponseView addSysUser(HttpSession session, SysUser user) {
		//如果该用户不是超级用户
		SysUser loginUser = (SysUser) session.getAttribute("user");
		if(loginUser.getUserType() != 0) {
			ResponseView.unauthorized("无权操作");
		}
		if(StringUtils.isNullOrEmpty(user.getUserName())) {
			ResponseView.fail("用户名不能为空");
		}else if(StringUtils.isNullOrEmpty(user.getNickName())) {
			ResponseView.fail("昵称不能为空");
		}
		//默认密码,admin123
		user.setPassword("336ab5453477ee18eeef664b4ea54ff847afa46a08dbde5b3649c8244ea155bbf6a32e63800a6ced");
		user.setState(0);
		user.setSex(1);
		user.setUserType(1);
		user.setCreateTime(new Date());
		user.setCreateUserId(loginUser.getId());
		s.addSysUser(user);
		return ResponseView.ok();
	}
	
	/**
	 * a修改此账号登录ip
	 * @param session
	 * @param id 用户id
	 * @param ip
	 * @return
	 */
	@PostMapping("/addAllowLoginIp")
	public ResponseView addAllowLoginIp(HttpSession session, Long id, String ip) {
		//如果该用户不是超级用户
		SysUser loginUser = (SysUser) session.getAttribute("user");
		if(loginUser.getUserType() != 0) {
			ResponseView.unauthorized("无权操作");
		}
		if(id == null) {
			ResponseView.unauthorized("id不能为空");
		}
		SysUser user = new SysUser();
		user.setId(id);
		user.setAllowLoginIp(ip);
		s.updateByPrimaryKeySelective(user);
		return ResponseView.ok();
	}
	
	/**
	 * a修改此账号登录ip
	 * @param session
	 * @param ip
	 * @return
	 */
	@PostMapping("/resetPassword")
	public ResponseView resetPassword(HttpSession session, Long id) {
		//如果该用户不是超级用户
		SysUser loginUser = (SysUser) session.getAttribute("user");
		if(loginUser.getUserType() != 0) {
			ResponseView.unauthorized("无权操作");
		}
		if(id == null) {
			ResponseView.unauthorized("id不能为空");
		}
		SysUser user = new SysUser();
		user.setId(id);
		//恢复默认密码
		user.setPassword("336ab5453477ee18eeef664b4ea54ff847afa46a08dbde5b3649c8244ea155bbf6a32e63800a6ced");
		int updateByPrimaryKeySelective = s.updateByPrimaryKeySelective(user);
		return ResponseView.ok(updateByPrimaryKeySelective);
	}
}
