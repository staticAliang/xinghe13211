package com.fengshen.server.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fengshen.core.util.Utils;

/**
 * 启动成功后通知界面
 *
 */
@Component
@Order(0)
public class ProjectStarterSuccess implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		if(System.getProperty("os.name").toLowerCase().indexOf("linux") == -1) {
			LoginAuth.success.setIcon(null);
			LoginAuth.success.setText("欢迎使用-"+LoginAuth.gameName);
			LoginAuth.openManage.setVisible(true);
			LoginAuth.timeOutError.cancel();
		}
	}

}