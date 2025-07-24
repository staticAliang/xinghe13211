package com.fengshen.db.game;

import java.util.HashMap;
import java.util.Map;

public class Static {
	public static Map<String, Integer> CHAR_TOKEN_DATA;

	static {
		Static.CHAR_TOKEN_DATA = new HashMap<String, Integer>();
	}
	
	public static final String CACHE_PERFIX_NAME = "GAMECACHE-";
}
