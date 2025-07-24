package com.fengshen.server.process.safelock;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_INFO;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_INFO;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求设置密码
 * 
 *
 */
@Service
@Slf4j
public class CMD_SAFE_LOCK_SET implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		String key = GameReadTool.readString(buff);
		String pwd = GameReadTool.readString(buff);
		log.info("请求设置安全密码,key={},pwd={}",key,pwd);
		if(gameObjectChar == null) {
			return;
		}
		//设置安全密码
		Accounts account = GameData.that.baseAccountsService.findOneByToken(gameObjectChar.accountToken);
		if(account == null) {
			ctx.close();
			return;
		}
		account.setKeyword(pwd);
		account.setUpdateTime(LocalDateTime.now());
		GameData.that.baseAccountsService.updateById(account);
		//刷新密码信息
		Vo_SAFE_LOCK_INFO vo = new Vo_SAFE_LOCK_INFO();
		vo.setHasPwd(1);
		vo.setIsRelleaseLock(gameObjectChar.relleaseLock);
		vo.setResetStart(0);
		vo.setResetEnd(0);
		vo.setResetDays(7);
		gameObjectChar.sendOne(new MSG_SAFE_LOCK_INFO(), vo);
		gameObjectChar.safeLockPwd = pwd;
		GameUtil.sendMeTips("设置成功！");
	}

	@Override
	public int cmd() {
		return 0x803C;
	}

}
