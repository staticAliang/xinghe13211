package com.fengshen.db.dao;

import org.apache.ibatis.annotations.*;

import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;

import java.util.*;

public interface NoticeMapper
{
    long countByExample(final NoticeExample paramNoticeExample);
    
    int deleteByExample(final NoticeExample paramNoticeExample);
    
    int deleteByPrimaryKey(final Integer paramInteger);
    
    int insert(final Notice paramNotice);
    
    int insertSelective(final Notice paramNotice);
    
    Notice selectOneByExample(final NoticeExample paramNoticeExample);
    
    Notice selectOneByExampleSelective(@Param("example") final NoticeExample paramNoticeExample, @Param("selective") final Notice.Column... paramVarArgs);
    
    List<Notice> selectByExampleSelective(@Param("example") final NoticeExample paramNoticeExample, @Param("selective") final Notice.Column... paramVarArgs);
    
    List<Notice> selectByExample(final NoticeExample paramNoticeExample);
    
    Notice selectByPrimaryKeySelective(@Param("id") final Integer paramInteger, @Param("selective") final Notice.Column... paramVarArgs);
    
    Notice selectByPrimaryKey(final Integer paramInteger);
    
    Notice selectByPrimaryKeyWithLogicalDelete(@Param("id") final Integer paramInteger, @Param("andLogicalDeleted") final boolean paramBoolean);
    
    int updateByExampleSelective(@Param("record") final Notice paramNotice, @Param("example") final NoticeExample paramNoticeExample);
    
    int updateByExample(@Param("record") final Notice paramNotice, @Param("example") final NoticeExample paramNoticeExample);
    
    int updateByPrimaryKeySelective(final Notice paramNotice);
    
    int updateByPrimaryKey(final Notice paramNotice);
    
    int logicalDeleteByExample(@Param("example") final NoticeExample paramNoticeExample);
    
    int logicalDeleteByPrimaryKey(final Integer paramInteger);
}
