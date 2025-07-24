package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.ShowTasksMapper;
import com.fengshen.db.domain.ShowTasks;
import com.fengshen.db.domain.example.ShowTasksExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseShowTasksService {
	@Autowired
	protected ShowTasksMapper mapper;

	public ShowTasks findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public ShowTasks findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final ShowTasks showTasks) {
		showTasks.setAddTime(LocalDateTime.now());
		showTasks.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(showTasks);
	}

	public int updateById(final ShowTasks showTasks) {
		showTasks.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(showTasks);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<ShowTasks> findByTaskType(final String taskType) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskTypeEqualTo(taskType);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByTaskDesc(final String taskDesc) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskDescEqualTo(taskDesc);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByTaskPrompt(final String taskPrompt) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskPromptEqualTo(taskPrompt);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByRefresh(final Integer refresh) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRefreshEqualTo(refresh);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByTaskEndTime(final Integer taskEndTime) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskEndTimeEqualTo(taskEndTime);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByAttrib(final Integer attrib) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByReward(final String reward) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRewardEqualTo(reward);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByShowName(final String showName) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andShowNameEqualTo(showName);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByTasktaskExtraPara(final String tasktaskExtraPara) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTasktaskExtraParaEqualTo(tasktaskExtraPara);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findByTasktaskState(final Integer tasktaskState) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTasktaskStateEqualTo(tasktaskState);
		return this.mapper.selectByExample(example);
	}

	public ShowTasks findOneByTaskType(final String taskType) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskTypeEqualTo(taskType);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByTaskDesc(final String taskDesc) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskDescEqualTo(taskDesc);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByTaskPrompt(final String taskPrompt) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskPromptEqualTo(taskPrompt);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByRefresh(final Integer refresh) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRefreshEqualTo(refresh);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByTaskEndTime(final Integer taskEndTime) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTaskEndTimeEqualTo(taskEndTime);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByAttrib(final Integer attrib) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByReward(final String reward) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRewardEqualTo(reward);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByShowName(final String showName) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andShowNameEqualTo(showName);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByTasktaskExtraPara(final String tasktaskExtraPara) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTasktaskExtraParaEqualTo(tasktaskExtraPara);
		return this.mapper.selectOneByExample(example);
	}

	public ShowTasks findOneByTasktaskState(final Integer tasktaskState) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTasktaskStateEqualTo(tasktaskState);
		return this.mapper.selectOneByExample(example);
	}

	public List<ShowTasks> findAll(final int page, final int size, final String sort, final String order) {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<ShowTasks> findAll() {
		final ShowTasksExample example = new ShowTasksExample();
		final ShowTasksExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
