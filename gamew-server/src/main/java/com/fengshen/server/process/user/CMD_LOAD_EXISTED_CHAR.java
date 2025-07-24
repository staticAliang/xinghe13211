package com.fengshen.server.process.user;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.BlackList;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.service.system.BlackListService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.user.Vo_OTHER_LOGIN;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.data.write.user.MSG_OTHER_LOGIN;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.job.SaveCharaTimes;
import com.fengshen.server.util.GameConfig;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

// 完成角色初始化的一些操作
@Service
@Slf4j
public class CMD_LOAD_EXISTED_CHAR implements GameHandler {
	
	@Autowired
	private BlackListService blackListService;
	
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		String char_name = GameReadTool.readString(buff);
		GameObjectChar session = GameObjectChar.getGameObjectChar();
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
		try {
			GameUtil.openDlg("WaitDlg");
			String clientIp = ipSocket.getAddress().getHostAddress();
			//账号信息
			Accounts account = session.getAccount();
			if(account == null) {
				ctx.close();
				return;
			}
			// 如果服务器开启维护了,只允许调试人员进入
			if (GameConfig.config.getBaseConfig().getStopServer() == 1) {
				//如果该账号在测试名单中就忽略
				String testList = GameData.that.redisUtils.get("testList");
				boolean contains = false;
				if(testList != null) {
					contains = testList.contains(account.getName());
				}
				if(!contains) {
					if (account.getPrivilege() != 1000) {
						GameObjectChar.send(new MSG_KICK_OFF(), "区组维护。");
						GameObjectCharMng.del(session);
						ctx.close();
						return;
					}
				}
			}
			if(account.getId()>0) {
				if(StringUtils.isNullOrEmpty(account.getPassword()) || "default_pwd".equals(account.getPassword())) {
					GameObjectChar.send(new MSG_KICK_OFF(), "请初始化登录密码，否则无法进入游戏！");
					ctx.close();
					return;
				}
			}
			Example example = new Example(BlackList.class);
			example.createCriteria().orEqualTo("data", clientIp).orEqualTo("data", account.getMac());
			if (blackListService.selectCountByExample(example) != 0) {
				if (session != null) {
					session.sendOne(new MSG_KICK_OFF(), "你因涉嫌违规，已被禁止登录游戏");
				}
				ctx.close();
				return;
			}
			Characters characters = null;
			boolean isFastLogin = false;
			//判断该账号下是否有角色登录
			List<Characters> onLineChar = GameData.that.characterService.getOnLineChar(session.accountid,"name","id");
			if (session.chara == null) {
				characters = GameData.that.characterService.login(account.getId(), char_name);
				if (characters == null) {
					session.sendOne(new MSG_KICK_OFF(), "角色不存在");
					ctx.close();
					return;
				}
				// 如果角色被封
				if (characters.getBlock() == 1) {
					session.sendOne(new MSG_KICK_OFF(), "此角色已被封闭");
					ctx.close();
					return;
				}
				if(characters.getLastLoginTime() == null || characters.getLastLoginTime() == 0) {
					isFastLogin = true;
				}else {
					GameObjectChar oldSession = GameObjectCharMng.getGameObjectChar(characters.getId());
					// 该用户已被登录上线.
					if (oldSession != null) {
						SaveCharaTimes.saveCharaInfo(oldSession);
						Characters newChar = GameData.that.characterService.login(session.accountid, char_name);
						//被顶号的话就在查询一次.
						Vo_OTHER_LOGIN login = new Vo_OTHER_LOGIN();
						login.setCode(0);
						login.setResult(2);
						login.setMsg("你的账号已在其他设备登录,如非本人操作请尽快修改密码！");
						oldSession.sendOne(new MSG_OTHER_LOGIN(), login);
						oldSession.ctx.close();
						session.init(newChar);
						//设置上次地图信息
						session.gameMap = oldSession.gameMap;
						session.tickCount = new AtomicInteger(0);
						session.shiDaoFlag = oldSession.getShiDaoFlag();
						session.shiDaoGetReward = oldSession.shiDaoGetReward;
						//开始加载上次地图信息
						GameCommonUtil.loadExistedChar(newChar, session, char_name);
						log.info("顶号处理.........");
						return;
					}
				}
				//账号下没有任何角色登录的时候才初始化
				if(onLineChar.isEmpty()) {
					session.init(characters);
				}
			}else {
				characters = session.characters;
				if(characters.getLastLoginTime() == null || characters.getLastLoginTime() == 0) {
					isFastLogin = true;
				}
			}
			//判断该账号下是否有角色登录
			if(!onLineChar.isEmpty()) {
				//该账号已有角色在线
				Characters onlineCharacters = onLineChar.get(0);
				if(!characters.getGid().equals(onlineCharacters.getGid())) {
					//找到这个人
					GameObjectChar oldSession = GameObjectCharMng.getGameObjectChar(onlineCharacters.getId());
					Map<String,Object> data = new HashMap<>();
					data.put("gameObjectChara", oldSession);
					data.put("char_name", onlineCharacters.getName());
					data.put("clientIp", clientIp);
					data.put("lastLoginIp", account.getLastLoginIp());
					session.confirmData = data;
					GameUtil.confirm(session, "当前账号下角色#Y"+onlineCharacters.getName()+"#n正在游戏中，无法使用其他角色登录，确定要执行顶号操作登录#Y"+onlineCharacters.getName()+"#n吗？", "topLogin");
					return;
				}
			}
			//正常登录
			GameCommonUtil.loadExistedChar(characters, session, char_name);
			//如果是第一次注册登录
			if(isFastLogin) {
				Vo_61553_0 task = session.chara.taskMap.get("主线—浮生若梦");
				if(task != null && "主线—浮生若梦_s0".equals(session.chara.current_task)) {
					//创建黄仨儿npc，24：110
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 1000;
					npc.id = 333333333;
					npc.x = 24;
					npc.y = 110;
					npc.icon = 6018;
					npc.type = 2;
					npc.org_icon = 6018;
					npc.portrait = 6018;
					npc.name = "黄仨儿";
					session.sendOne(new M65529_0(), npc);
					//剧情播放
					task.task_state = "1";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(session.chara, GameData.that.baseNpcDialogueService.findById(656));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
			}
		} finally {
			GameUtil.closeDlg("WaitDlg");
		}
	}

	@Override
	public int cmd() {
		return 4192;
	}

	public static void main(final String[] args) throws UnsupportedEncodingException {
		final String value = String.valueOf("多闻道人");
		final byte[] bs = value.getBytes("GBK");
		final String s = bytesToHexString(bs);
		System.out.println(s);
	}

	public static String bytesToHexString(final byte[] src) {
		final StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; ++i) {
			final int v = src[i] & 0xFF;
			final String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			result = new byte[++hexlen / 2];
			inHex = "0" + inHex;
		} else {
			result = new byte[hexlen / 2];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			++j;
		}
		return result;
	}

	public static byte hexToByte(final String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}
}