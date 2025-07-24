package com.fengshen.server.auth;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.Application;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.util.GameConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginAuth {

	public static JFrame jFrame = new JFrame("登录");
	private Container c = jFrame.getContentPane();
	private JTextField username = new JTextField();
	private JPasswordField password = new JPasswordField();
	private BufferedImage image = null;
	private JPanel titlePanel;
	private JPanel fieldPanel;
	private JPanel buttonPanel;
	private static String locaMac;
	public static JLabel lable;
	public static JLabel success;
	public static JButton openManage;
	private SystemTray systemTray;//
	
	private TrayIcon trayIcon;
	public static Timer timeOutError;

	public static String gameName = "";
	public void run() throws Exception {
		gameName = "星河";
		//读取配置文件,如果不存在直接结束程序.
//		String jsonStr = "";
//		try {
//			File resFile = Utils.getResFile("secret.key");
//			byte[] readAllBytes = Files.readAllBytes(resFile.toPath());
//			jsonStr = new String(readAllBytes);
//		} catch (IOException e) {
//			JOptionPane.showMessageDialog(null, "启动失败,授权文件不存在,程序即将退出", "授权失败", JOptionPane.ERROR_MESSAGE);
//			System.exit(-1);
//		}
		//对数据进行解密
		try {
//			JSONObject parseObject = JSONObject.parseObject(GameCommonUtil.abc(jsonStr));
//			locaMac = parseObject.getString("mac");
//			//校验时间是否满足， 如果当前大于授权时间.则直接结束
//			String endTime = parseObject.getString("endTime");
//			Date parse = DateUtil.getSdf("yyyy-MM-dd H:mm").parse(endTime);
//			if(parse == null) {
//				JOptionPane.showMessageDialog(null, "授权时间已过期", "授权失败", JOptionPane.ERROR_MESSAGE);
//				System.exit(-1);
//				return;
//			}
//			long currentTime = DateUtil.getWebsiteDatetime().getTime();
//			if(currentTime>parse.getTime()) {
//				JOptionPane.showMessageDialog(null, "授权时间已过期", "授权失败", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//			long time = (parse.getTime()-currentTime);
			//如果时间满足,则开启定时器.
//			new Timer().schedule(new TimerTask(){
//				@Override
//				public void run() {
//					try {
//						//设置状态
//						GameCore.isExpire.set(true);
//						//全部玩家都下线
//						List<GameObjectChar> all = GameObjectCharMng.getAll();
//						for(GameObjectChar game:all) {
//							game.sendOne(new MSG_KICK_OFF(), "你已被强制下线！");
//							try {
//								game.offline();
//							}catch (Exception e) {
//								e.printStackTrace();
//							}
//						}
//					}finally {
//						JOptionPane.showMessageDialog(null, "授权时间已过期", "授权失败", JOptionPane.ERROR_MESSAGE);
//						//授权时间到了，结束程序
//						System.exit(-1);
//					}
//				}
//			}, time);
		} catch (Exception e) {
			e.printStackTrace();
			//解密失败
			JOptionPane.showMessageDialog(null, "授权文件异常,程序即将退出", "授权失败", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return;
		}
		if (SystemTray.isSupported()) {
			systemTray = SystemTray.getSystemTray();
			// 设置托管
			trayIcon = new TrayIcon(ImageIO.read(new ClassPathResource("game.png").getInputStream()));
			trayIcon.setImageAutoSize(true);
			systemTray.add(trayIcon);
			trayIcon.setToolTip(gameName);
			jFrame.addWindowListener(new WindowAdapter() {
				public void windowIconified(WindowEvent e) {
					jFrame.dispose();
				}
				public void windowClosing(WindowEvent e) {
					LoginAuth.success.setText("正在存档，请勿强制关闭,,");
					//关闭窗口
					int response = JOptionPane.showConfirmDialog(null, "确认关闭吗？", "关闭程序", JOptionPane.YES_NO_OPTION);
			        if(response==0){
			        	//确定
			        	jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        	//进行存档一次
			        	List<GameObjectChar> all = GameObjectCharMng.getAll();
			    		for(GameObjectChar g:all) {
		    				Chara chara = g.chara;
		    				try {
		    					//如果在战斗则直接退出战斗
			    				if(chara.isFight) {
			    					FightContainer fightContainer = FightManager.getFightContainer(chara.id);
			    					if (fightContainer != null) {
			    						FightManager.listFight.remove(fightContainer);
			    						FightManager.sendOver(fightContainer, true);
			    					}
			    				}
				            	g.offline();
							} catch (Exception e2) {
								log.error("{}",e);
							}
		    			}
			    		Iterator<Integer> iterator = GameCore.luoboTaoziCids.iterator();
						while(iterator.hasNext()) {
							Integer id = iterator.next();
							GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
							if(gameObjectChar != null) {
								//删除任务
								gameObjectChar.chara.taskMap.remove("萝卜桃子大收集");
								gameObjectChar.lbtzTaskCount = 1;
							}else {
								//数据库查询
								Characters ch = GameData.that.baseCharactersService.findOneByIdSelectProperties(id, "id","data");
								Chara chara = JSONObject.parseObject(ch.getData(),Chara.class);
								chara.taskMap.remove("萝卜桃子大收集");
								ch.setData(JSONObject.toJSONString(chara));
								//保存
								GameData.that.baseCharactersService.updateByPrimaryKeySelective(ch);
							}
						}
			    		//关闭整个程序
			        	System.exit(0);
			        }else {
			        	//否则
			        	jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			        	LoginAuth.success.setText("欢迎使用-"+LoginAuth.gameName);
			        }
				}
			});

			trayIcon.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2)// 双击托盘窗口再现
						jFrame.setExtendedState(Frame.NORMAL);
					jFrame.setVisible(true);
				}
			});
			
		}
		try {
			ClassPathResource c = new ClassPathResource("game.png");
			image = ImageIO.read(c.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		jFrame.setIconImage(image);
		// 设置窗体的位置及大小
		jFrame.setSize(320, 220);
		
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width/2; // 获取屏幕的宽
		int screenHeight = screenSize.height/2; // 获取屏幕的高
		int height = jFrame.getHeight();
		int width = jFrame.getWidth();
		jFrame.setLocation(screenWidth-width/2, screenHeight-height/2);
		// 设置一层相当于桌布的东西
		c.setLayout(new BorderLayout());// 布局管理器
		// 设置按下右上角X号后关闭
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 初始化--往窗体里放其他控件
		init();
		// 设置窗体可见
		jFrame.setVisible(true);
		jFrame.setResizable(false);

	}

	public void init() throws IOException {
		/* 标题部分--North */
		titlePanel = new JPanel();
		titlePanel.setLayout(new FlowLayout());
		titlePanel.add(new JLabel(gameName));
		c.add(titlePanel, "North");

		/* 输入部分--Center */
		fieldPanel = new JPanel();
//		fieldPanel.setLayout(null);
//		a1.setBounds(50, 20, 50, 20);
//		a2.setBounds(50, 60, 50, 20);
//		fieldPanel.add(a1);
//		fieldPanel.add(a2);
//		username.setBounds(110, 20, 120, 20);
//		password.setBounds(110, 60, 120, 20);
//		fieldPanel.add(username);
//		fieldPanel.add(password);
//		c.add(fieldPanel, "Center");
//
//		/* 按钮部分--South */
//		buttonPanel = new JPanel();
//		buttonPanel.setLayout(new FlowLayout());
//		buttonPanel.add(okbtn);
//		buttonPanel.add(cancelbtn);
//		c.add(buttonPanel, "South");

//		okbtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				try {
//					login();
//				} catch (HeadlessException e1) {
//					e1.printStackTrace();
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//			}
//		});
		
		
//		String getLocaMac = Utils.getLocalMac().trim();
//		locaMac = locaMac.trim();
//		String locaMac2 = Utils.replaceBom(locaMac);
//		if (!locaMac2.equals(getLocaMac)) {
//			JOptionPane.showMessageDialog(null, "请使用指定机器登录("+Utils.getLocalMac()+")", "机器认证失败("+locaMac.toUpperCase()+")", JOptionPane.ERROR_MESSAGE);
//			System.exit(0);
//			return;
//		} else  {
			titlePanel.setVisible(false);
			File resFile2 = Utils.getResFile("config.json");
			byte[] readAllBytes2 = Files.readAllBytes(resFile2.toPath());
			JSONObject jsonObject = JSONObject.parseObject(new String(readAllBytes2,"UTF-8"));
			JSONObject baseConfig = jsonObject.getJSONObject("baseConfig");
			String customeName = baseConfig.getString("gameName");
			if(customeName == null) {
				customeName = "";
			}else {
				customeName = "("+customeName+")";
			}
			jFrame.setTitle(gameName+"-"+GameCommonUtil.gameVersion+customeName);
			trayIcon.setToolTip(jFrame.getTitle());
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			openManage = new JButton();
			openManage.setLayout(null);
			openManage.setVisible(false);
			openManage.setText("进入后台");
			openManage.setLocation(20, 100);
			openManage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Runtime.getRuntime().exec("cmd /c start " + GameConfig.config.getBaseConfig().getManageLink());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			buttonPanel.add(openManage);
			c.add(buttonPanel, "South");
			// 加载启动项目
			InputStream load = new ClassPathResource("loading-2.gif").getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*4];
			int n = 0;
			while (-1 != (n = load.read(buffer))) {
				output.write(buffer, 0, n);
			}
			ImageIcon i = new ImageIcon(buffer);
			lable = new JLabel(i);
			c.add(lable);
			success = new JLabel(i);
			success.setText("正在启动项目");
			c.add(success);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					SpringApplication.run(Application.class, new String[] {});
				}
				
			}, 2000);
			//如果3分钟未启动
			timeOutError = new Timer();
			timeOutError.schedule(new TimerTask() {
				@Override
				public void run() {
					LoginAuth.success.setIcon(null);
					LoginAuth.success.setText("启动失败,请联系开发者");
					log.error("启动失败...");
				}
			}, 3*60*1000);
//		}
		
	}

	public void login() throws HeadlessException, Exception {
		String usernameText = username.getText();
		String passwordText = new String(password.getPassword());
		String getLocaMac = Utils.getLocalMac().trim();
		locaMac = locaMac.trim();
		String locaMac2 = Utils.replaceBom(locaMac);
		if (!locaMac2.equals(getLocaMac)) {
			JOptionPane.showMessageDialog(null, "请使用指定机器登录("+Utils.getLocalMac()+")", "机器认证失败("+locaMac.toUpperCase()+")", JOptionPane.ERROR_MESSAGE);
		} else if (Utils.authName.equals(usernameText) && Utils.authPassword.equals(passwordText)) {
			JOptionPane.showMessageDialog(null, "成功登录", "认证成功", JOptionPane.PLAIN_MESSAGE);
			titlePanel.setVisible(false);
			fieldPanel.setVisible(false);
			buttonPanel.setVisible(false);
			
			File resFile2 = Utils.getResFile("config.json");
			byte[] readAllBytes2 = Files.readAllBytes(resFile2.toPath());
			JSONObject jsonObject = JSONObject.parseObject(new String(readAllBytes2,"UTF-8"));
			JSONObject baseConfig = jsonObject.getJSONObject("baseConfig");
			String customeName = baseConfig.getString("gameName");
			if(customeName == null) {
				customeName = "";
			}else {
				customeName = "("+customeName+")";
			}
			jFrame.setTitle(gameName+"-"+GameCommonUtil.gameVersion+customeName);
			trayIcon.setToolTip(jFrame.getTitle());
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			openManage = new JButton();
			openManage.setLayout(null);
			openManage.setVisible(false);
			openManage.setText("进入后台");
			openManage.setLocation(20, 100);
			openManage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Runtime.getRuntime().exec("cmd /c start " + GameConfig.config.getBaseConfig().getManageLink());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			buttonPanel.add(openManage);
			c.add(buttonPanel, "South");
			// 加载启动项目
			InputStream load = new ClassPathResource("loading-2.gif").getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024*4];
			int n = 0;
			while (-1 != (n = load.read(buffer))) {
				output.write(buffer, 0, n);
			}
			ImageIcon i = new ImageIcon(buffer);
			lable = new JLabel(i);
			c.add(lable);
			success = new JLabel(i);
			success.setText("正在启动项目");
			c.add(success);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					SpringApplication.run(Application.class, new String[] {});
				}
				
			}, 2000);
			//如果3分钟未启动
			timeOutError = new Timer();
			timeOutError.schedule(new TimerTask() {
				@Override
				public void run() {
					LoginAuth.success.setIcon(null);
					LoginAuth.success.setText("启动失败,请联系开发者");
					log.error("启动失败...");
				}
			}, 3*60*1000);
		}else {
			JOptionPane.showMessageDialog(null, "授权失败,用户名或密码错误", "认证失败", JOptionPane.ERROR_MESSAGE);
		}
	}
}