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
 * 解锁安全密码
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_SAFE_LOCK_CHANGE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String key = GameReadTool.readString(buff);
		String oldPwd = GameReadTool.readString(buff);
		String newPwd = GameReadTool.readString(buff);
		log.info("修改安全密码,key={}，oldPwd={},newPwd={}",key,oldPwd,newPwd);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		if(!gameObjectChar.safeLockPwd.equals(oldPwd)) {
			GameUtil.sendMeTips("原密码错误");
			return;
		}else if(newPwd.length()<4 || oldPwd.length()<4) {
			GameUtil.sendMeTips("密码长度不正确");
			return;
		}else if(newPwd.equals(oldPwd)) {
			GameUtil.sendMeTips("原密码不能和新密码相同");
			return;
		}
		//设置新的安全密码
		Accounts account = GameData.that.baseAccountsService.findById(gameObjectChar.accountid);
		if(account == null) {
			ctx.close();
			return;
		}
		account.setKeyword(newPwd);
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
		gameObjectChar.safeLockPwd = newPwd;
		//清除限制
		GameData.that.redisUtils.delete("SAFE_LOCK_UNLOCK_ERROR_INFO_"+gameObjectChar.chara.id);
		//刷新
		GameUtil.sendMeTips("安全密码修改成功！");
	}

	@Override
	public int cmd() {
		return 0x803E;
	}

}
