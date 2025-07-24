package com.fengshen.server.process.auth;

import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_61537_0;
import com.fengshen.server.data.vo.Vo_61537_0;
import com.fengshen.server.data.vo.account.Vo_L_ACCOUNT_CHARS;
import com.fengshen.server.data.vo.account.Vo_L_ACCOUNT_CHARS.Role;
import com.fengshen.server.data.write.M61537_0;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.data.write.account.MSG_L_ACCOUNT_CHARS;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.ServerHandler;
import com.fengshen.server.process.user.CMD_CREATE_NEW_CHAR;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;

/**
 * 角色登录
 * 
 *
 */
@Service
@Slf4j
public class CMD_LOGIN implements GameHandler {

	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		// user是用户的token，通过token获取玩家角色
		String user = GameReadTool.readString(buff);
		int auth_key = GameReadTool.readInt(buff);
		int seed = GameReadTool.readInt(buff);
		//模拟器
		int emulator = GameReadTool.readByte(buff);
		int sight_scope = GameReadTool.readByte(buff);
		String version = GameReadTool.readString(buff);
		String clientid = GameReadTool.readString(buff);
		int netStatus = GameReadTool.readShort(buff);
		int adult = GameReadTool.readByte(buff);
		String signature = GameReadTool.readString(buff);
		String clientname = GameReadTool.readString(buff);
		int redfinger = GameReadTool.readByte(buff);
		log.info("角色登录， user={},auth_key={},seed={},emulator={},sight_scope={},version={},clientid={},netStatus={},adult={},signature={},clientname={},redfinger={}",
				user,auth_key,seed,emulator,sight_scope,version,clientid,netStatus,adult,signature,clientname,redfinger);
		//不允许红手指登录--关闭红手指
//		if(redfinger == 1) {
//			ByteBuf write = new MSG_KICK_OFF().write("禁止使用红手指，第三方登录器登录。");
//			ctx.writeAndFlush(write);
//			ctx.close();
//			return;
//		}
		user = user.substring(6);
		InetSocketAddress ipSocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIp = ipSocket.getAddress().getHostAddress();
		// 查询玩家账号
		String illegalUser = GameData.that.redisUtils.get("illegal_"+clientIp);
		//查询是否存在黑名单
		if(StringUtils.isNoneEmpty(illegalUser) && Integer.valueOf(illegalUser) > 2) {
			ByteBuf write = new MSG_KICK_OFF().write("服务器拒绝了你的登录请求！");
			ctx.writeAndFlush(write);
			ctx.close();
			return;
		}
		Accounts accounts = GameData.that.baseAccountsService.findOneByToken(user);
		if(accounts == null) {
			//如果次数超过2次为空,直接把该请求的客户端IP拉黑
			GameData.that.redisUtils.getIncr2("illegal_"+clientIp);
			ByteBuf write = new MSG_KICK_OFF().write("非法登录，次数超限将封号！");
			ctx.writeAndFlush(write);
			ctx.close();
			return;
		}
		
		// 获取到角色列表
		Characters where = new Characters();
		where.setAccountId(accounts.getId());
		List<Characters> charactersList = GameData.that.characterService.listjiaose(where,
				"lastLoginTime", "online","gid","name", "level", "polar", "portrait", "monthTao");
		ListVo_61537_0 listvo_61537_0 = CMD_CREATE_NEW_CHAR.listjiaose(charactersList);
		ByteBuf write = new M61537_0().write(listvo_61537_0);
		ctx.writeAndFlush(write);
		//区组角色信息
		Vo_L_ACCOUNT_CHARS charas = new Vo_L_ACCOUNT_CHARS();
		charas.setDistName(GameConfig.lineName);
		for(Vo_61537_0 vo:listvo_61537_0.vo_61537_0) {
			Role role = new Role();
			role.setDeleteTime(0);
			role.setIcon(vo.type);
			role.setName(vo.str);
			role.setLevel(vo.skill);
			charas.getRoleList().add(role);
		}
		ctx.writeAndFlush(new MSG_L_ACCOUNT_CHARS().write(charas));
		
		GameObjectChar gameSession = new GameObjectChar(accounts, ctx);
		Attribute<GameObjectChar> attr = ctx.channel().attr(ServerHandler.akey);
		attr.set(gameSession);
	}

	@Override
	public int cmd() {
		return 12290;
	}
}