package com.fengshen.db.dao;

import java.util.*;

import com.fengshen.db.domain.*;

public interface RenwuResetMapper
{
    List<RenwuReset> select(final RenwuReset renwuReset);
    
    int insert(final RenwuReset renwuReset);
    
    int update(final RenwuReset renwuReset);
    
    int delete();
}
