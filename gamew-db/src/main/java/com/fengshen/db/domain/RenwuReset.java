package com.fengshen.db.domain;

import java.io.*;

public class RenwuReset implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    private int accountId;
    private int renwuStatus;
    
    public int getAccountId() {
        return this.accountId;
    }
    
    public void setAccountId(final int accountId) {
        this.accountId = accountId;
    }
    
    public int getRenwuStatus() {
        return this.renwuStatus;
    }
    
    public void setRenwuStatus(final int renwuStatus) {
        this.renwuStatus = renwuStatus;
    }
}
