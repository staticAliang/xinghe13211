package com.fengshen.db.domain.example;

import java.util.*;

import com.fengshen.db.domain.*;

import java.time.*;

public class ShowTasksExample
{
    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;
    
    public ShowTasksExample() {
        this.oredCriteria = new ArrayList<Criteria>();
    }
    
    public void setOrderByClause(final String orderByClause) {
        this.orderByClause = orderByClause;
    }
    
    public String getOrderByClause() {
        return this.orderByClause;
    }
    
    public void setDistinct(final boolean distinct) {
        this.distinct = distinct;
    }
    
    public boolean isDistinct() {
        return this.distinct;
    }
    
    public List<Criteria> getOredCriteria() {
        return this.oredCriteria;
    }
    
    public void or(final Criteria criteria) {
        this.oredCriteria.add(criteria);
    }
    
    public Criteria or() {
        final Criteria criteria = this.createCriteriaInternal();
        this.oredCriteria.add(criteria);
        return criteria;
    }
    
    public ShowTasksExample orderBy(final String orderByClause) {
        this.setOrderByClause(orderByClause);
        return this;
    }
    
    public ShowTasksExample orderBy(final String... orderByClauses) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < orderByClauses.length; ++i) {
            sb.append(orderByClauses[i]);
            if (i < orderByClauses.length - 1) {
                sb.append(" , ");
            }
        }
        this.setOrderByClause(sb.toString());
        return this;
    }
    
    public Criteria createCriteria() {
        final Criteria criteria = this.createCriteriaInternal();
        if (this.oredCriteria.size() == 0) {
            this.oredCriteria.add(criteria);
        }
        return criteria;
    }
    
    protected Criteria createCriteriaInternal() {
        final Criteria criteria = new Criteria(this);
        return criteria;
    }
    
    public void clear() {
        this.oredCriteria.clear();
        this.orderByClause = null;
        this.distinct = false;
    }
    
    public static Criteria newAndCreateCriteria() {
        final ShowTasksExample example = new ShowTasksExample();
        return example.createCriteria();
    }
    
    public ShowTasksExample when(final boolean condition, final IExampleWhen then) {
        if (condition) {
            then.example(this);
        }
        return this;
    }
    
    public ShowTasksExample when(final boolean condition, final IExampleWhen then, final IExampleWhen otherwise) {
        if (condition) {
            then.example(this);
        }
        else {
            otherwise.example(this);
        }
        return this;
    }
    
    public static class Criteria extends GeneratedCriteria
    {
        private ShowTasksExample example;
        
        protected Criteria(final ShowTasksExample example) {
            this.example = example;
        }
        
        public ShowTasksExample example() {
            return this.example;
        }
        
        @Deprecated
        public Criteria andIf(final boolean ifAdd, final ICriteriaAdd add) {
            if (ifAdd) {
                add.add(this);
            }
            return this;
        }
        
        public Criteria when(final boolean condition, final ICriteriaWhen then) {
            if (condition) {
                then.criteria(this);
            }
            return this;
        }
        
        public Criteria when(final boolean condition, final ICriteriaWhen then, final ICriteriaWhen otherwise) {
            if (condition) {
                then.criteria(this);
            }
            else {
                otherwise.criteria(this);
            }
            return this;
        }
        
        public Criteria andLogicalDeleted(final boolean deleted) {
            return deleted ? this.andDeletedEqualTo(ShowTasks.Deleted.IS_DELETED.value()) : this.andDeletedNotEqualTo(ShowTasks.Deleted.IS_DELETED.value());
        }
        
        @Deprecated
        public interface ICriteriaAdd
        {
            Criteria add(final Criteria paramCriteria);
        }
    }
    
    protected abstract static class GeneratedCriteria
    {
        protected List<Criterion> criteria;
        
        protected GeneratedCriteria() {
            this.criteria = new ArrayList<Criterion>();
        }
        
        public boolean isValid() {
            return this.criteria.size() > 0;
        }
        
        public List<Criterion> getAllCriteria() {
            return this.criteria;
        }
        
        public List<Criterion> getCriteria() {
            return this.criteria;
        }
        
        protected void addCriterion(final String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            this.criteria.add(new Criterion(condition));
        }
        
        protected void addCriterion(final String condition, final Object value, final String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value));
        }
        
        protected void addCriterion(final String condition, final Object value1, final Object value2, final String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            this.criteria.add(new Criterion(condition, value1, value2));
        }
        
        public Criteria andIdIsNull() {
            this.addCriterion("id is null");
            return (Criteria)this;
        }
        
        public Criteria andIdIsNotNull() {
            this.addCriterion("id is not null");
            return (Criteria)this;
        }
        
        public Criteria andIdEqualTo(final Integer value) {
            this.addCriterion("id =", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("id = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualTo(final Integer value) {
            this.addCriterion("id <>", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("id <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThan(final Integer value) {
            this.addCriterion("id >", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("id > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("id >=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("id >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThan(final Integer value) {
            this.addCriterion("id <", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("id < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualTo(final Integer value) {
            this.addCriterion("id <=", value, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("id <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andIdIn(final List<Integer> values) {
            this.addCriterion("id in", values, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotIn(final List<Integer> values) {
            this.addCriterion("id not in", values, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id between", value1, value2, "id");
            return (Criteria)this;
        }
        
        public Criteria andIdNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("id not between", value1, value2, "id");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeIsNull() {
            this.addCriterion("task_type is null");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeIsNotNull() {
            this.addCriterion("task_type is not null");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeEqualTo(final String value) {
            this.addCriterion("task_type =", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_type = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotEqualTo(final String value) {
            this.addCriterion("task_type <>", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_type <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThan(final String value) {
            this.addCriterion("task_type >", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_type > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThanOrEqualTo(final String value) {
            this.addCriterion("task_type >=", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_type >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThan(final String value) {
            this.addCriterion("task_type <", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_type < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThanOrEqualTo(final String value) {
            this.addCriterion("task_type <=", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_type <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeLike(final String value) {
            this.addCriterion("task_type like", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotLike(final String value) {
            this.addCriterion("task_type not like", value, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeIn(final List<String> values) {
            this.addCriterion("task_type in", values, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotIn(final List<String> values) {
            this.addCriterion("task_type not in", values, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeBetween(final String value1, final String value2) {
            this.addCriterion("task_type between", value1, value2, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskTypeNotBetween(final String value1, final String value2) {
            this.addCriterion("task_type not between", value1, value2, "taskType");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescIsNull() {
            this.addCriterion("task_desc is null");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescIsNotNull() {
            this.addCriterion("task_desc is not null");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescEqualTo(final String value) {
            this.addCriterion("task_desc =", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_desc = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskDescNotEqualTo(final String value) {
            this.addCriterion("task_desc <>", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_desc <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskDescGreaterThan(final String value) {
            this.addCriterion("task_desc >", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_desc > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskDescGreaterThanOrEqualTo(final String value) {
            this.addCriterion("task_desc >=", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_desc >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskDescLessThan(final String value) {
            this.addCriterion("task_desc <", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_desc < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskDescLessThanOrEqualTo(final String value) {
            this.addCriterion("task_desc <=", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_desc <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskDescLike(final String value) {
            this.addCriterion("task_desc like", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescNotLike(final String value) {
            this.addCriterion("task_desc not like", value, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescIn(final List<String> values) {
            this.addCriterion("task_desc in", values, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescNotIn(final List<String> values) {
            this.addCriterion("task_desc not in", values, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescBetween(final String value1, final String value2) {
            this.addCriterion("task_desc between", value1, value2, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskDescNotBetween(final String value1, final String value2) {
            this.addCriterion("task_desc not between", value1, value2, "taskDesc");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptIsNull() {
            this.addCriterion("task_prompt is null");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptIsNotNull() {
            this.addCriterion("task_prompt is not null");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptEqualTo(final String value) {
            this.addCriterion("task_prompt =", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_prompt = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptNotEqualTo(final String value) {
            this.addCriterion("task_prompt <>", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_prompt <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptGreaterThan(final String value) {
            this.addCriterion("task_prompt >", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_prompt > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptGreaterThanOrEqualTo(final String value) {
            this.addCriterion("task_prompt >=", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_prompt >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptLessThan(final String value) {
            this.addCriterion("task_prompt <", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_prompt < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptLessThanOrEqualTo(final String value) {
            this.addCriterion("task_prompt <=", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_prompt <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptLike(final String value) {
            this.addCriterion("task_prompt like", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptNotLike(final String value) {
            this.addCriterion("task_prompt not like", value, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptIn(final List<String> values) {
            this.addCriterion("task_prompt in", values, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptNotIn(final List<String> values) {
            this.addCriterion("task_prompt not in", values, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptBetween(final String value1, final String value2) {
            this.addCriterion("task_prompt between", value1, value2, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andTaskPromptNotBetween(final String value1, final String value2) {
            this.addCriterion("task_prompt not between", value1, value2, "taskPrompt");
            return (Criteria)this;
        }
        
        public Criteria andRefreshIsNull() {
            this.addCriterion("refresh is null");
            return (Criteria)this;
        }
        
        public Criteria andRefreshIsNotNull() {
            this.addCriterion("refresh is not null");
            return (Criteria)this;
        }
        
        public Criteria andRefreshEqualTo(final Integer value) {
            this.addCriterion("refresh =", value, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("refresh = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRefreshNotEqualTo(final Integer value) {
            this.addCriterion("refresh <>", value, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("refresh <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRefreshGreaterThan(final Integer value) {
            this.addCriterion("refresh >", value, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("refresh > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRefreshGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("refresh >=", value, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("refresh >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRefreshLessThan(final Integer value) {
            this.addCriterion("refresh <", value, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("refresh < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRefreshLessThanOrEqualTo(final Integer value) {
            this.addCriterion("refresh <=", value, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("refresh <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRefreshIn(final List<Integer> values) {
            this.addCriterion("refresh in", values, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshNotIn(final List<Integer> values) {
            this.addCriterion("refresh not in", values, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshBetween(final Integer value1, final Integer value2) {
            this.addCriterion("refresh between", value1, value2, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andRefreshNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("refresh not between", value1, value2, "refresh");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeIsNull() {
            this.addCriterion("task_end_time is null");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeIsNotNull() {
            this.addCriterion("task_end_time is not null");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeEqualTo(final Integer value) {
            this.addCriterion("task_end_time =", value, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_end_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeNotEqualTo(final Integer value) {
            this.addCriterion("task_end_time <>", value, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_end_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeGreaterThan(final Integer value) {
            this.addCriterion("task_end_time >", value, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_end_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("task_end_time >=", value, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_end_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeLessThan(final Integer value) {
            this.addCriterion("task_end_time <", value, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("task_end_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeLessThanOrEqualTo(final Integer value) {
            this.addCriterion("task_end_time <=", value, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("task_end_time <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeIn(final List<Integer> values) {
            this.addCriterion("task_end_time in", values, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeNotIn(final List<Integer> values) {
            this.addCriterion("task_end_time not in", values, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeBetween(final Integer value1, final Integer value2) {
            this.addCriterion("task_end_time between", value1, value2, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andTaskEndTimeNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("task_end_time not between", value1, value2, "taskEndTime");
            return (Criteria)this;
        }
        
        public Criteria andAttribIsNull() {
            this.addCriterion("attrib is null");
            return (Criteria)this;
        }
        
        public Criteria andAttribIsNotNull() {
            this.addCriterion("attrib is not null");
            return (Criteria)this;
        }
        
        public Criteria andAttribEqualTo(final Integer value) {
            this.addCriterion("attrib =", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("attrib = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribNotEqualTo(final Integer value) {
            this.addCriterion("attrib <>", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("attrib <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThan(final Integer value) {
            this.addCriterion("attrib >", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("attrib > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("attrib >=", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("attrib >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThan(final Integer value) {
            this.addCriterion("attrib <", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("attrib < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanOrEqualTo(final Integer value) {
            this.addCriterion("attrib <=", value, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("attrib <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAttribIn(final List<Integer> values) {
            this.addCriterion("attrib in", values, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotIn(final List<Integer> values) {
            this.addCriterion("attrib not in", values, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribBetween(final Integer value1, final Integer value2) {
            this.addCriterion("attrib between", value1, value2, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andAttribNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("attrib not between", value1, value2, "attrib");
            return (Criteria)this;
        }
        
        public Criteria andRewardIsNull() {
            this.addCriterion("reward is null");
            return (Criteria)this;
        }
        
        public Criteria andRewardIsNotNull() {
            this.addCriterion("reward is not null");
            return (Criteria)this;
        }
        
        public Criteria andRewardEqualTo(final String value) {
            this.addCriterion("reward =", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("reward = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRewardNotEqualTo(final String value) {
            this.addCriterion("reward <>", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("reward <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRewardGreaterThan(final String value) {
            this.addCriterion("reward >", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("reward > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRewardGreaterThanOrEqualTo(final String value) {
            this.addCriterion("reward >=", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("reward >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRewardLessThan(final String value) {
            this.addCriterion("reward <", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("reward < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRewardLessThanOrEqualTo(final String value) {
            this.addCriterion("reward <=", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("reward <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andRewardLike(final String value) {
            this.addCriterion("reward like", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardNotLike(final String value) {
            this.addCriterion("reward not like", value, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardIn(final List<String> values) {
            this.addCriterion("reward in", values, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardNotIn(final List<String> values) {
            this.addCriterion("reward not in", values, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardBetween(final String value1, final String value2) {
            this.addCriterion("reward between", value1, value2, "reward");
            return (Criteria)this;
        }
        
        public Criteria andRewardNotBetween(final String value1, final String value2) {
            this.addCriterion("reward not between", value1, value2, "reward");
            return (Criteria)this;
        }
        
        public Criteria andShowNameIsNull() {
            this.addCriterion("show_name is null");
            return (Criteria)this;
        }
        
        public Criteria andShowNameIsNotNull() {
            this.addCriterion("show_name is not null");
            return (Criteria)this;
        }
        
        public Criteria andShowNameEqualTo(final String value) {
            this.addCriterion("show_name =", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("show_name = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowNameNotEqualTo(final String value) {
            this.addCriterion("show_name <>", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("show_name <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowNameGreaterThan(final String value) {
            this.addCriterion("show_name >", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("show_name > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowNameGreaterThanOrEqualTo(final String value) {
            this.addCriterion("show_name >=", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("show_name >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowNameLessThan(final String value) {
            this.addCriterion("show_name <", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("show_name < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowNameLessThanOrEqualTo(final String value) {
            this.addCriterion("show_name <=", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("show_name <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andShowNameLike(final String value) {
            this.addCriterion("show_name like", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameNotLike(final String value) {
            this.addCriterion("show_name not like", value, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameIn(final List<String> values) {
            this.addCriterion("show_name in", values, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameNotIn(final List<String> values) {
            this.addCriterion("show_name not in", values, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameBetween(final String value1, final String value2) {
            this.addCriterion("show_name between", value1, value2, "showName");
            return (Criteria)this;
        }
        
        public Criteria andShowNameNotBetween(final String value1, final String value2) {
            this.addCriterion("show_name not between", value1, value2, "showName");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaIsNull() {
            this.addCriterion("tasktask_extra_para is null");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaIsNotNull() {
            this.addCriterion("tasktask_extra_para is not null");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaEqualTo(final String value) {
            this.addCriterion("tasktask_extra_para =", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_extra_para = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaNotEqualTo(final String value) {
            this.addCriterion("tasktask_extra_para <>", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_extra_para <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaGreaterThan(final String value) {
            this.addCriterion("tasktask_extra_para >", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_extra_para > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaGreaterThanOrEqualTo(final String value) {
            this.addCriterion("tasktask_extra_para >=", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_extra_para >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaLessThan(final String value) {
            this.addCriterion("tasktask_extra_para <", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_extra_para < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaLessThanOrEqualTo(final String value) {
            this.addCriterion("tasktask_extra_para <=", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_extra_para <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaLike(final String value) {
            this.addCriterion("tasktask_extra_para like", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaNotLike(final String value) {
            this.addCriterion("tasktask_extra_para not like", value, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaIn(final List<String> values) {
            this.addCriterion("tasktask_extra_para in", values, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaNotIn(final List<String> values) {
            this.addCriterion("tasktask_extra_para not in", values, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaBetween(final String value1, final String value2) {
            this.addCriterion("tasktask_extra_para between", value1, value2, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskExtraParaNotBetween(final String value1, final String value2) {
            this.addCriterion("tasktask_extra_para not between", value1, value2, "tasktaskExtraPara");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateIsNull() {
            this.addCriterion("tasktask_state is null");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateIsNotNull() {
            this.addCriterion("tasktask_state is not null");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateEqualTo(final Integer value) {
            this.addCriterion("tasktask_state =", value, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_state = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateNotEqualTo(final Integer value) {
            this.addCriterion("tasktask_state <>", value, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_state <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateGreaterThan(final Integer value) {
            this.addCriterion("tasktask_state >", value, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_state > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateGreaterThanOrEqualTo(final Integer value) {
            this.addCriterion("tasktask_state >=", value, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_state >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateLessThan(final Integer value) {
            this.addCriterion("tasktask_state <", value, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_state < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateLessThanOrEqualTo(final Integer value) {
            this.addCriterion("tasktask_state <=", value, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("tasktask_state <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateIn(final List<Integer> values) {
            this.addCriterion("tasktask_state in", values, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateNotIn(final List<Integer> values) {
            this.addCriterion("tasktask_state not in", values, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateBetween(final Integer value1, final Integer value2) {
            this.addCriterion("tasktask_state between", value1, value2, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andTasktaskStateNotBetween(final Integer value1, final Integer value2) {
            this.addCriterion("tasktask_state not between", value1, value2, "tasktaskState");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeIsNull() {
            this.addCriterion("add_time is null");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeIsNotNull() {
            this.addCriterion("add_time is not null");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time =", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("add_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <>", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("add_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("add_time >", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("add_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time >=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("add_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThan(final LocalDateTime value) {
            this.addCriterion("add_time <", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("add_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("add_time <=", value, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("add_time <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andAddTimeIn(final List<LocalDateTime> values) {
            this.addCriterion("add_time in", values, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotIn(final List<LocalDateTime> values) {
            this.addCriterion("add_time not in", values, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andAddTimeNotBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeIsNull() {
            this.addCriterion("update_time is null");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeIsNotNull() {
            this.addCriterion("update_time is not null");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time =", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("update_time = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <>", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("update_time <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThan(final LocalDateTime value) {
            this.addCriterion("update_time >", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("update_time > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time >=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("update_time >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThan(final LocalDateTime value) {
            this.addCriterion("update_time <", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("update_time < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualTo(final LocalDateTime value) {
            this.addCriterion("update_time <=", value, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("update_time <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeIn(final List<LocalDateTime> values) {
            this.addCriterion("update_time in", values, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotIn(final List<LocalDateTime> values) {
            this.addCriterion("update_time not in", values, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andUpdateTimeNotBetween(final LocalDateTime value1, final LocalDateTime value2) {
            this.addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria)this;
        }
        
        public Criteria andDeletedIsNull() {
            this.addCriterion("deleted is null");
            return (Criteria)this;
        }
        
        public Criteria andDeletedIsNotNull() {
            this.addCriterion("deleted is not null");
            return (Criteria)this;
        }
        
        public Criteria andDeletedEqualTo(final Boolean value) {
            this.addCriterion("deleted =", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("deleted = " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualTo(final Boolean value) {
            this.addCriterion("deleted <>", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("deleted <> " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThan(final Boolean value) {
            this.addCriterion("deleted >", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanColumn(final ShowTasks.Column column) {
            this.addCriterion("deleted > " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted >=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedGreaterThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("deleted >= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThan(final Boolean value) {
            this.addCriterion("deleted <", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanColumn(final ShowTasks.Column column) {
            this.addCriterion("deleted < " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualTo(final Boolean value) {
            this.addCriterion("deleted <=", value, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedLessThanOrEqualToColumn(final ShowTasks.Column column) {
            this.addCriterion("deleted <= " + column.getEscapedColumnName());
            return (Criteria)this;
        }
        
        public Criteria andDeletedIn(final List<Boolean> values) {
            this.addCriterion("deleted in", values, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotIn(final List<Boolean> values) {
            this.addCriterion("deleted not in", values, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedBetween(final Boolean value1, final Boolean value2) {
            this.addCriterion("deleted between", value1, value2, "deleted");
            return (Criteria)this;
        }
        
        public Criteria andDeletedNotBetween(final Boolean value1, final Boolean value2) {
            this.addCriterion("deleted not between", value1, value2, "deleted");
            return (Criteria)this;
        }
    }
    
    public static class Criterion
    {
        private String condition;
        private Object value;
        private Object secondValue;
        private boolean noValue;
        private boolean singleValue;
        private boolean betweenValue;
        private boolean listValue;
        private String typeHandler;
        
        public String getCondition() {
            return this.condition;
        }
        
        public Object getValue() {
            return this.value;
        }
        
        public Object getSecondValue() {
            return this.secondValue;
        }
        
        public boolean isNoValue() {
            return this.noValue;
        }
        
        public boolean isSingleValue() {
            return this.singleValue;
        }
        
        public boolean isBetweenValue() {
            return this.betweenValue;
        }
        
        public boolean isListValue() {
            return this.listValue;
        }
        
        public String getTypeHandler() {
            return this.typeHandler;
        }
        
        protected Criterion(final String condition) {
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }
        
        protected Criterion(final String condition, final Object value, final String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List) {
                this.listValue = true;
            }
            else {
                this.singleValue = true;
            }
        }
        
        protected Criterion(final String condition, final Object value) {
            this(condition, value, null);
        }
        
        protected Criterion(final String condition, final Object value, final Object secondValue, final String typeHandler) {
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }
        
        protected Criterion(final String condition, final Object value, final Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
    
    public interface ICriteriaWhen
    {
        void criteria(final Criteria paramCriteria);
    }
    
    public interface IExampleWhen
    {
        void example(final ShowTasksExample paramShowTasksExample);
    }
}
