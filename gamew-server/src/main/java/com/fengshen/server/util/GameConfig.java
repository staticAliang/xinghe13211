package com.fengshen.server.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.domain.config.*;
import com.fengshen.server.game.GameData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Pet;
import com.fengshen.server.domain.ConfigInit;

@Component
public class GameConfig {
	
	private static final Logger log = LoggerFactory.getLogger(GameConfig.class);
	
    public static int BAXIANTIAOZHAN;
    public static int XIANGYAO_NUM;
    public static int LY_SHUADAO_NUM;
    public static int jifenhuishou;
    //海盗刷新坐标.
    public static String[] haidaoPosition;
    
    //新增
    public static String lineName;

    // 设置GM指令的MAP表
    public static Map<String, String> gmCommandMap;
    ///参战boos
    public static Map<String, Object> canzhanBoos = new ConcurrentHashMap<>();
    //基础配置
    public static ConfigInit config;
    //神魂配置
    public static ShenHunConfig shenHunConfig;
    //装备改造配置
    public static EquipGaiZaoConfig equipGaiZaoConfig;
	//天书配置
	public static TianshuConfig tianshuConfig;
    //强制PK配置
    public static ForcePkConfig forcePkConfig;
    //抽奖
    public static ChoujiangConfig choujiangConfig;
    //附灵
    public static List<SpiritInfoConfig> spiritInfoConfig;
    
    public static Integer tongtiantaTao;
    
    //宠物缓存
    public static List<Pet> petCache;

    public static String serverIp;
    
    public static int port;
    
    public static String path;
    
    //武器改造配置
    public static Map<String,String> equipGaiZao;

    
    //装备属性最大值
    public static JSONObject equipAttchMax;
    //stdValue
    public static JSONObject stdValue;
    
    public static NeiDanConfig neiDanConfig;
    
    public static TyzqAttribConfig tyzqAttribConfig;
    //功能组
    public static String menuAuths;
    //桃子萝卜状态
    public static int taoziLuoboStatus;
    


    @Value("${lineName}")
    public void setLineName(final String lineName) {
    	GameConfig.lineName = lineName;
    }
    
    @Value("${netty.ip}")
    public String setNettyIp(String nettyIp) {
		return GameConfig.serverIp = nettyIp;
	}
    @Value("${netty.port}")
    public int setNettyPort(int port) {
		return GameConfig.port = port;
	}
    
    @Value("${jifenhuishou}")
	public void setJifenhuishou(int jifenhuishou) {
		GameConfig.jifenhuishou = jifenhuishou;
	}
    
    @Value("${tongtiantaTao}")
	public void setTongtiantaTao(Integer tongtiantaTao) {
		GameConfig.tongtiantaTao = tongtiantaTao;
	}
    
    @Value("${server.servlet.context-path}")
	public static void setPath(String path) {
		GameConfig.path = path;
	}
    
    @Value("${menu_auths}")
   	public void setMenuAuths(String menu_auths) {
   		GameConfig.menuAuths = menu_auths;
   	}


	public static byte[] readStream(InputStream inStream){
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		return outSteam.toByteArray();
	}
    
    static {
    	 //获取基础配置文件.
		try {
			File resFile = Utils.getResFile("config.json");
			InputStream inputStream = new FileInputStream(resFile);
			config = JSONObject.parseObject(Utils.read(inputStream), ConfigInit.class);
			if(config.getBaseConfig().getCommonAddSpeedCount() == 0) {
				//设置默认次数
				config.getBaseConfig().setCommonAddSpeedCount(50);
			}
			log.info("成功初始化基础配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		//神魂
		try {
			File resFile = Utils.getResFile("shenhun.json");
			InputStream inputStream = new FileInputStream(resFile);
			shenHunConfig = JSONObject.parseObject(Utils.read(inputStream), ShenHunConfig.class);
			log.info("成功初始化神魂配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		
		
		//改造配置
		try {
			File resFile = Utils.getResFile("equip_gaizao.json");
			InputStream inputStream = new FileInputStream(resFile);
			equipGaiZaoConfig = JSONObject.parseObject(Utils.read(inputStream), EquipGaiZaoConfig.class);
			log.info("成功初始化改造配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}

		//天书配置
		try {
			File resFile = Utils.getResFile("tianshu.json");
			InputStream inputStream = new FileInputStream(resFile);
			tianshuConfig = JSONObject.parseObject(Utils.read(inputStream), TianshuConfig.class);
			log.info("成功初始化天书配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		
		//强制PK
		try {
			File resFile = Utils.getResFile("force_pk_config.json");
			InputStream inputStream = new FileInputStream(resFile);
			forcePkConfig = JSONObject.parseObject(Utils.read(inputStream), ForcePkConfig.class);
			log.info("成功初始化强制PK配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		
		//充值好礼配置
		try {
			File resFile = Utils.getResFile("choujiang_config.json");
			InputStream inputStream = new FileInputStream(resFile);
			choujiangConfig = JSONObject.parseObject(Utils.read(inputStream), ChoujiangConfig.class);
			log.info("成功初始化充值好礼配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		//附灵配置
		try {
			File resFile = Utils.getResFile("SpiritInfoCfg.json");
			InputStream inputStream = new FileInputStream(resFile);
			spiritInfoConfig = JSONObject.parseArray(Utils.read(inputStream), SpiritInfoConfig.class);
			log.info("成功初始化附灵配置");
		} catch (IOException e) {
			log.error("读取配置文件失败：{}","{}");
			System.exit(-1);
		}
		
		//太阴之气配置
		tyzqAttribConfig = JSONObject.parseObject(readSystemJson("static/tyzqAttribList.json"), TyzqAttribConfig.class);
		log.info("成功初始化太阴之气配置");
		
		//装备最大值配置
		equipAttchMax = JSONObject.parseObject(readSystemJson("static/EquipAttchMax.json"));
		log.info("成功初始化装备属性最大值配置");
		
		//stdVaue配置
		stdValue = JSONObject.parseObject(readSystemJson("static/stdValue.json"));
		log.info("成功初始化stdValue配置");
		
        GameConfig.BAXIANTIAOZHAN = config.getBaseConfig().getBaxianNum();
        GameConfig.XIANGYAO_NUM = 1;
        GameConfig.LY_SHUADAO_NUM = 1;
        //加载gm配置
        loadGmConfig(config);
        String haidao = "80#94)81#92)73#92)76#86)67#87)67#88)76#85)81#83)79#84)72#80)71#75)63#70)57#62)53#59)59#55)61#50)60#55)57#62)63#67)70#72)79#74)75#66)81#62)84#57)79#58)89#57)82#55)76#49)83#49)83#49)85#46)82#40)74#44)73#36)71#30)80#30)86#24)86#15)85#17)79#18)69#21)71#29)68#35)61#28)57#27)65#23)60#24)52#28)53#32)53#32)45#33)45#26)46#26)38#25)40#23)44#15)44#21)35#19)31#11)29#8)23#11)25#13)27#16)27#16)29#22)34#29)43#33)38#36)30#32)30#38)28#43)24#35)15#31)18#31)22#31)17#32)19#39)15#46)24#47)29#47)20#51)15#56)15#58)21#62)20#66)10#65)10#64)12#71)11#74)20#73)29#76)29#77)20#75)19#79)19#83)10#85)19#83)19#86)23#86)30#87)27#85)32#81)24#84)15#88)17#95)14#97)24#96)19#93)16#88)25#84)31#77)31#78)40#80)47#78)46#81)37#85)41#90)50#89)47#80)55#76)57#75)52#82)47#89)56#91)65#92)66#84)61#85)69#90)78#93)69#92)65#94)";
		haidaoPosition = haidao.split("\\)");
		//单独读取后台访问前缀
		Properties properties = new Properties();
	    // 使用ClassLoader加载properties配置文件生成对应的输入流
	    InputStream in = GameConfig.class.getClassLoader().getResourceAsStream("application.properties");
	    // 使用properties对象加载输入流
	    try {
			properties.load(in);
			//获取key对应的value值
			path = properties.getProperty("server.servlet.context-path");
		} catch (IOException e) {
		}
	    equipGaiZao = new HashMap<>();
	    equipGaiZao.put("n1", GameConfig.equipGaiZaoConfig.n1Type);
	    equipGaiZao.put("n2", GameConfig.equipGaiZaoConfig.n2Type);
	    equipGaiZao.put("n3", GameConfig.equipGaiZaoConfig.n3Type);
	    equipGaiZao.put("n4", GameConfig.equipGaiZaoConfig.n4Type);
	    equipGaiZao.put("n5", GameConfig.equipGaiZaoConfig.n5Type);
	    equipGaiZao.put("n6", GameConfig.equipGaiZaoConfig.n6Type);
	    equipGaiZao.put("n7", GameConfig.equipGaiZaoConfig.n7Type);
	    equipGaiZao.put("n8", GameConfig.equipGaiZaoConfig.n8Type);
	    equipGaiZao.put("n9", GameConfig.equipGaiZaoConfig.n9Type);
	    equipGaiZao.put("n10", GameConfig.equipGaiZaoConfig.n10Type);
	    equipGaiZao.put("n11", GameConfig.equipGaiZaoConfig.n11Type);
	    
    }
    //加载gm配置
    public static void loadGmConfig(ConfigInit config) {
    	// 初始化GM指令默认全部关闭；0闭1开
        GameConfig.gmCommandMap = new HashMap<>();
        GameConfig.gmCommandMap.put("all", "0");
        GameConfig.gmCommandMap.put("czsyrrw", "0");
        GameConfig.gmCommandMap.put("czrw", "0");
        GameConfig.gmCommandMap.put("yesheng", "0");
        GameConfig.gmCommandMap.put("baobao", "0");
    }
    
    /**
	 * 读取json
	 * @param path
	 * @return
	 */
	private static String readSystemJson(String path) {
		PathMatchingResourcePatternResolver pr = new PathMatchingResourcePatternResolver();
		Resource resource = pr.getResource(path);
		BufferedReader br = null;
		try {
			InputStream inputStream = resource.getInputStream();
			InputStreamReader fr = new InputStreamReader(inputStream, "UTF-8");
			br = new BufferedReader(fr);
		} catch (IOException e) {
			log.error("加载:{}失败",path,e);
			System.exit(0);
		}
		StringBuilder sb = new StringBuilder();
		br.lines().forEach((f) -> {
			sb.append(f);
		});
		return sb.toString();
	}
}
