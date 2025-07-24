package com.fengshen.db.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.SysUserMapper;
import com.fengshen.db.domain.sys.SysUser;

import tk.mybatis.mapper.entity.Example;

@Service
public class SysUserService implements BaseServiceSupport<SysUser>{

	@Autowired
	private SysUserMapper sum;
	
	/**
	 * 用户登录
	 * @param username 账户
	 * @param password 密码
	 * @return
	 */
	public SysUser login(String username, String password) {
		Example example = new Example(SysUser.class);
		example.createCriteria().andEqualTo("password", password).andEqualTo("userName", username);
		SysUser login = sum.selectOneByExample(example);
		return login;
	}
	
	/**
	 * 添加用户
	 * @param su
	 * @return
	 */
	public int addSysUser(SysUser su) {
		
		return sum.insertSelective(su);
	}
	
	public int changePassword(SysUser su) {
		
		return sum.updateByPrimaryKeySelective(su);
	}
	
	public SysUser getUserById(Long id) {
		return sum.selectByPrimaryKey(id);
	}

	@Override
	public BaseCustomMapper<SysUser> getBaseMapper() {
		return sum;
	}
}
