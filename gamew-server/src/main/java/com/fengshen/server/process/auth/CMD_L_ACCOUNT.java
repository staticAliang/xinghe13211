package com.fengshen.server.process.auth;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_13143_0;
import com.fengshen.server.data.write.M13143_0;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录账号
 * 
 *
 */
@Service
@Slf4j
public class CMD_L_ACCOUNT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String type = GameReadTool.readString(buff);
		String account = GameReadTool.readString(buff);
		String password = GameReadTool.readString(buff);
		String mac = GameReadTool.readString(buff);
		String oaid = GameReadTool.readString(buff);
		String data = GameReadTool.readString(buff);
		String lock = GameReadTool.readString(buff);
		String dist = GameReadTool.readString(buff);
		int from3rdSdk = GameReadTool.readByte(buff);
		String channel = GameReadTool.readString(buff);
		String os_ver = GameReadTool.readString(buff);
		String term_info = GameReadTool.readString(buff);
		String imei = GameReadTool.readString(buff);
		String client_original_ver = GameReadTool.readString(buff);
		int not_replace = GameReadTool.readByte(buff);
		int oper_type = GameReadTool.readByte(buff);
		String m_value = GameReadTool.readString(buff);
		
		log.info("客户端请求登录账号，type={},account={},password={},mac={},oaid={},data={},lock={},dist={},from3rdSdk={},channel={},os_ver={},term_info={},imei={},client_original_ver={},not_replace={},oper_type={},m_value={}",
				type,account,password,mac,oaid,data,lock,dist,from3rdSdk,channel,os_ver,term_info,imei,client_original_ver
				,not_replace,oper_type,m_value);
		String token = account.substring(6);
		Accounts useraccount = GameData.that.baseAccountsService.findOneByToken(token);
		if (useraccount == null) {
			// 验证不通过
			ctx.writeAndFlush(new MSG_KICK_OFF().write("验证不通过"));
			ctx.close();
			return;
		}
		if (useraccount.getDeleted()) {
			ctx.writeAndFlush(new MSG_KICK_OFF().write("账号被封,无法登录。"));
			ctx.close();
			return;
		}
		Vo_13143_0 vo_13143_0 = new Vo_13143_0();
		vo_13143_0.result = 1;
		/**
		 * PRIVILEGE = { STANDARD = 0, -- Normal user ADMINISTRATOR = 120, -- Game
		 * administrator OBSERVER = 130, -- Game observer BEHOLDER = 200, -- Game
		 * beholder CONTROLLER = 300, -- Game controller DEBUGGER = 1000, -- Server
		 * debugger }
		 */
		vo_13143_0.privilege = useraccount.getPrivilege();
		vo_13143_0.ip = GameConfig.serverIp;
		vo_13143_0.port = GameConfig.port;
		vo_13143_0.seed = (int) (System.currentTimeMillis()/1000L);
//		vo_13143_0.seed = 1446640884;
		vo_13143_0.auth_key = useraccount.getId();
//		vo_13143_0.auth_key = 5955087;
		vo_13143_0.id = 1;
		vo_13143_0.serverName = GameConfig.lineName;
		vo_13143_0.serverStatus = 3;
		vo_13143_0.msg = "允许该账号登录";
		ByteBuf write = new M13143_0().write(vo_13143_0, new boolean[0]);
		ctx.writeAndFlush(write);
		//更新账号信息
		useraccount.setLastLoginMac(mac);
		GameData.that.baseAccountsService.updateById(useraccount);
		//存入当前缓存
		GameData.that.redisUtils.set(useraccount.getId()+"_login_step", "9040");
	}

	@Override
	public int cmd() {
		return 9040;
	}
}