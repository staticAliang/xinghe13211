package com.fengshen.server.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.fengshen.server.game.GameCore;

// 这里是游戏启动的入口！
@Component
@Order(1)
public class ApplicationNetty implements ApplicationRunner {
	@Autowired
	private GameCore gameCore;
	@Value("${netty.port}")
	private int port;
	@Value("${netty.ip}")
	private String ip;

	public static void main(String[] args) {
		SpringApplication.run(ApplicationNetty.class, args);
	}

	// 启动游戏的主方法，先启动NettyServer，在初始化游戏
	public void run(ApplicationArguments args) {
		NettyServer server = new NettyServer();
		server.start(port);
		this.gameCore.init(server);
	}
}
