package com.fengshen.db.service.friend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.FriendGroupMapper;
import com.fengshen.db.domain.FriendGroup;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

/**
 * 好友分组列表
 * 
 *
 */
@Service
public class FriendGroupService implements BaseServiceSupport<FriendGroup> {
	
	@Autowired
	private FriendGroupMapper fgm;

	@Override
	public BaseCustomMapper<FriendGroup> getBaseMapper() {
		return fgm;
	}

	/**
	 * 根据gid获取好友分组
	 * @param gid
	 * @return
	 */
	public List<FriendGroup> getFriendGroupsByGid(String gid) {
		Example example = new Example(FriendGroup.class);
		example.createCriteria().andEqualTo("gid", gid);
		return fgm.selectByExample(example);
	}

}