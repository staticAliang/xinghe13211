package com.fengshen.server.data.xls_config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.alibaba.fastjson.JSONObject;

public class XLSConfigMgr {


    private static HashMap<String, Object> caches = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(XLSConfigMgr.class);

    public static void loadXls(String name) {
    	PathMatchingResourcePatternResolver pr = new PathMatchingResourcePatternResolver();
    	Resource resource = pr.getResource("static/xls_config/dugeno.json");
        BufferedReader br = null;
        try {
            InputStream inputStream = resource.getInputStream();
            InputStreamReader fr = new InputStreamReader(inputStream,"UTF-8");
            br = new BufferedReader(fr);
            log.info("加载副本配置文件成功");
        } catch (IOException var4) {
            log.error("加载配置文件失败,{}",var4.getMessage());
            System.exit(0);
            return;
        }
        StringBuilder sb = new StringBuilder();
        br.lines().forEach((f) -> {
            sb.append(f);
        });
        DugenoCfg obj = JSONObject.parseObject(sb.toString(),DugenoCfg.class);
        obj.init();
        caches.put(name, obj);
    }

    public static void init() {
        loadXls("dugeno");
    }

    public static Object getCfg(String name) {
        return caches.get(name);
    }
    
    public static void main(String[] args) {
		init();
	}
}
