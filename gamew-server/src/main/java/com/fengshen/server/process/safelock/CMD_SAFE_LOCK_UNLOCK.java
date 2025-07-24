package com.fengshen.server.process.safelock;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_INFO;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_OPEN_UNLOCK;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_INFO;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_OPEN_UNLOCK;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求解锁
 * 
 *
 */
@Service
@Slf4j
public class CMD_SAFE_LOCK_UNLOCK implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String key = GameReadTool.readString(buff);
		String pwd = GameReadTool.readString(buff);
		log.info("请求解锁安全密码,key:{},pwd:{}",key,pwd);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		//错误五次
		String errorCountInfo = GameData.that.redisUtils.get("SAFE_LOCK_UNLOCK_ERROR_INFO_"+gameObjectChar.chara.id);
		if(errorCountInfo != null) {
			GameUtil.sendMeTips("密码被锁定，暂时无法验证！");
			return;
		}
		int errorCount = 0;
		if(!pwd.equals(gameObjectChar.safeLockPwd)) {
			//记录次数
			errorCount = GameData.that.redisUtils.getIncr2("SAFE_LOCK_UNLOCK_ERROR_COUNT_"+gameObjectChar.chara.id);
			if(errorCount>=5) {
				//一小时内无法验证
				GameData.that.redisUtils.set("SAFE_LOCK_UNLOCK_ERROR_INFO_"+gameObjectChar.chara.id, "", 3600);
				GameData.that.redisUtils.delete("SAFE_LOCK_UNLOCK_ERROR_COUNT_"+gameObjectChar.chara.id);
			}
			GameUtil.sendMeTips("验证失败！");
			//刷新验证窗口
			Vo_SAFE_LOCK_OPEN_UNLOCK vo = new Vo_SAFE_LOCK_OPEN_UNLOCK();
			vo.setErrorCount(errorCount);
			vo.setErrorCountMax(5);
			vo.setKey(GameCommonUtil.safeLockKey);
			gameObjectChar.sendOne(new MSG_SAFE_LOCK_OPEN_UNLOCK(), vo);
			return;
		}
		//解锁验证成功
		gameObjectChar.relleaseLock = 1;
		Vo_SAFE_LOCK_INFO lockInfo = new Vo_SAFE_LOCK_INFO();
		lockInfo.setHasPwd(1);
		lockInfo.setIsRelleaseLock(gameObjectChar.relleaseLock);
		lockInfo.setResetStart(0);
		lockInfo.setResetEnd(0);
		lockInfo.setResetDays(7);
		gameObjectChar.sendOne(new MSG_SAFE_LOCK_INFO(), lockInfo);
		//删除错误次数
		GameData.that.redisUtils.delete("SAFE_LOCK_UNLOCK_ERROR_COUNT_"+gameObjectChar.chara.id);
		//关闭解锁窗口
		GameUtil.closeDlg("SafeLockReleaseDlg");
		GameUtil.sendMeTips("验证成功");
	}

	@Override
	public int cmd() {
		return 0x8040;
	}

}