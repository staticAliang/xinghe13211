package com.fengshen.server.domain;

import java.util.*;

public class LieBiao
{
    public String ask_type;
    public String peer_name;
    public List<Duiyuan> duiyuanList;
    
    public LieBiao() {
        this.duiyuanList = new ArrayList<Duiyuan>();
    }
}
