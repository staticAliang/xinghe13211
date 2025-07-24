package com.fengshen.db.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.MailboxRefreshMapper;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class MailboxRefreshService implements BaseServiceSupport<MailboxRefresh> {

	@Autowired
	private MailboxRefreshMapper m;
	
	@Override
	public BaseCustomMapper<MailboxRefresh> getBaseMapper() {
		return m;
	}

	@Transactional(rollbackFor = Exception.class,transactionManager = "dgGameTransaction")
	public void addMail(MailboxRefresh mail) {
		m.insertSelective(mail);
	}
}