package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.domain.Chara;

public class GameTeam {
	public List<List<Chara>> liebiao;
	public List<Chara> duiwu;
	public List<Vo_4121_0> zhanliduiyuan;

	public GameTeam() {
		this.liebiao = new ArrayList<List<Chara>>();
		this.duiwu = new ArrayList<Chara>();
		this.zhanliduiyuan = new ArrayList<Vo_4121_0>();
	}
}
