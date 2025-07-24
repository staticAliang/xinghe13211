package com.fengshen.server.process.safelock;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_INFO;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_INFO;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_OPEN_CHANGE;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_OPEN_SET;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求打开安全锁界面
 * 
 *
 */
@Service
@Slf4j
public class CMD_SAFE_LOCK_OPEN_DLG implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String type = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Accounts accounts = GameData.that.baseAccountsService.findById(gameObjectChar.accountid);
		if(accounts == null) {
			ctx.close();
			return;
		}
		//打开设置密码界面
		if("SafeLockSetDlg".equals(type)) {
			gameObjectChar.sendOne(new MSG_SAFE_LOCK_OPEN_SET(), GameCommonUtil.safeLockKey);
		}else if("SafeLockReleaseDlg".equals(type)) { //请求验证密码
			GameCommonUtil.openSafeUnlockDlg(gameObjectChar);
		}else if("SafeLockModifyDlg".equals(type)){
			//请求修改密码
			gameObjectChar.sendOne(new MSG_SAFE_LOCK_OPEN_CHANGE(), GameCommonUtil.safeLockKey);
		}else {
			Vo_SAFE_LOCK_INFO vo = new Vo_SAFE_LOCK_INFO();
			int hasPwd = 1;
			if(com.mysql.jdbc.StringUtils.isNullOrEmpty(accounts.getKeyword().trim())) {
				hasPwd = 0;
			}
			vo.setHasPwd(hasPwd);
			vo.setIsRelleaseLock(gameObjectChar.relleaseLock);
			vo.setResetStart(0);
			vo.setResetEnd(0);
			vo.setResetDays(7);
			gameObjectChar.sendOne(new MSG_SAFE_LOCK_INFO(), vo);
		}
		log.info("请求安全锁界面,type={}",type);
	}

	@Override
	public int cmd() {
		return 0x803A;
	}

}
