package com.fengshen.db.dao.custom;

import java.util.*;
import org.apache.ibatis.annotations.*;

public interface CustomDailiMapper
{
    @Select({ "select `code`,COUNT(*) as num from accounts WHERE `code` LIKE CONCAT(#{name},'%') group by `code` ;" })
    List<Map> selectCount(@Param("name") final String paramString);
    
    @Select({ "select `code`,SUM(money) as num from charge WHERE `code` LIKE CONCAT(#{name},'%') and DATE(add_time) = DATE(CURDATE()-#{day})  group by `code` ;" })
    List<Map> selectMoney(@Param("name") final String paramString, @Param("day") final int paramInt);
    
    @Select({ "select COUNT(*) as num from accounts WHERE `CODE` LIKE CONCAT(#{name},'%') group by `code`;" })
    List<Map> selectAccount(@Param("name") final String paramString);
}
