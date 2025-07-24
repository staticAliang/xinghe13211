package com.fengshen.db.service.friend;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.FriendMapper;
import com.fengshen.db.domain.Friend;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class FriendService implements BaseServiceSupport<Friend> {

	@Autowired
	private FriendMapper fm;
	
	@Override
	public BaseCustomMapper<Friend> getBaseMapper() {
		return fm;
	}
	
	/**
	 * 添加好友
	 * @param friend
	 * @return
	 */
	public int addFriend(Friend friend) {
		friend.setAddTime(new Date());
		int insertSelective = fm.insertSelective(friend);
		return insertSelective;
	}
	
	/**
	 * 根据分组名称获取好友列表
	 * @param groupId 分组id
	 * @param gid 用户gid
	 * @return
	 */
	public List<Friend> getFriendByGroupName(String groupId, String gid) {
		Example example = new Example(Friend.class);
		example.createCriteria().andEqualTo("groupId", groupId).andEqualTo("gid", gid);
		return fm.selectByExample(example);
	}
}
