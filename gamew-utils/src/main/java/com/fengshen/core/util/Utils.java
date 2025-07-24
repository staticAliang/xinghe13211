package com.fengshen.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.qcloud.cos.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Utils {

	private static final char[] HEXES = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	@Value("${spring.datasource.game.username}")
	private String username;
	@Value("${spring.datasource.game.password}")
	private String password;
	@Value("${spring.datasource.game.url}")
	private String url;
	@Value("${spring.datasource.game.driverClassName}")
	private String driverClassName;

	public final static String authName = "admin";
	public final static String authPassword = "admin123";

	/**
	 * 比较两个时间是否相等,精确到秒 08:38:00
	 * 
	 * @param time
	 * @return
	 */
	public static boolean compareHourOfMinute(String time) {
		boolean flag = false;
		if (!StringUtils.isNullOrEmpty(time)) {
			String currTime = DateUtil.format(new Date(), "H:mm:ss");
			if (currTime.equals(time)) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 比较两个时间是否相等
	 * 
	 * @param time    时间
	 * @param pattern 自定义
	 * @return
	 */
	public static boolean compareHourOfMinute(String time, String pattern) {
		boolean flag = false;
		if (!StringUtils.isNullOrEmpty(time)) {
			String currTime = DateUtil.format(new Date(), pattern);
			if (currTime.equals(time)) {
				flag = true;
			}
		}
		return flag;
	}

	public static String getWeek() {
		String week = "";
		Date today = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(today);
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		if (weekday == 1) {
			week = "周日";
		} else if (weekday == 2) {
			week = "周一";
		} else if (weekday == 3) {
			week = "周二";
		} else if (weekday == 4) {
			week = "周三";
		} else if (weekday == 5) {
			week = "周四";
		} else if (weekday == 6) {
			week = "周五";
		} else if (weekday == 7) {
			week = "周六";
		}
		return week;
	}

	public static File getResFile(String filename) {
		File file = new File(filename);
		if (!file.exists()) { // 如果同级目录没有，则去config下面找
			file = new File("config/" + filename);
		}
		FileSystemResource resource = new FileSystemResource(file);
		if (!resource.exists()) { // config目录下还是找不到，那就直接用classpath下的
			try {
				file = ResourceUtils.getFile("classpath:" + filename);
			} catch (FileNotFoundException e) {
				log.error("读取{}文件失败", filename);
			}
		}
		return file;
	}

	/**
	 * 获取本机mac地址
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getLocalMac() {
		byte[] mac = null;
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			// 获取网卡，获取地址
			mac = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
		} catch (Exception e) {
		}
		StringBuffer sb = new StringBuffer("");
		if(mac != null) {
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
//				sb.append("-");
				}
				// 字节转换为整数
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
		}
		return sb.toString().toUpperCase();
	}

	/**
	 * 计算两个时间差
	 * 
	 * @param lockTime
	 * @return
	 */
	public static Map<String, Long> calculationTime(Date lockTime) {
		// 一天的毫秒数
		long nd = 1000 * 24 * 60 * 60;
		// 一小时的毫秒数
		long nh = 1000 * 60 * 60;
		// 一分钟的毫秒数
		long nm = 1000 * 60;
		// 一秒钟的毫秒数long
		long ns = 1000;
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 获得两个时间的毫秒时间差异
		long diff;
		long day = 0;
		long hour = 0;
		long min;
		long sec;
		Map<String, Long> timeMap = new HashMap<String, Long>();
		try {

			diff = sd.parse(sd.format(new Date())).getTime() - sd.parse(sd.format(lockTime)).getTime();
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			min = diff % nd % nh / nm;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒//输出结果
			timeMap.put("day", day);
			timeMap.put("hours", (day * 24) + hour);
			timeMap.put("hour", hour);
			timeMap.put("min", min);
			timeMap.put("sec", sec);
			for (Map.Entry<String, Long> s : timeMap.entrySet()) {
				System.out.println(s.getKey() + ":" + s.getValue());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeMap;
	}

	/**
	 * 获取剩余多少时间.转时分
	 * 
	 * @param time
	 * @return
	 */
	public static String getGapTime(long time) {
		long hours = time / (1000 * 60 * 60);
		long minutes = (time - hours * (1000 * 60 * 60)) / (1000 * 60);
		String diffTime = "";
		if (minutes < 10) {
			diffTime = hours + "小时" + minutes + "分钟";
		} else {
			diffTime = hours + "小时" + minutes + "分钟";
		}
		return diffTime;
	}

	public static String read(InputStream is) {
		BufferedReader br = null;
		try {
			InputStreamReader fr = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(fr);
		} catch (IOException var4) {
			log.error("读取文件失败,{}", var4.getMessage());
			System.exit(0);
		}
		StringBuilder sb = new StringBuilder();
		br.lines().forEach((f) -> {
			sb.append(f);
		});
		return sb.toString();
	}

	public static String replaceBom(String str) {
		char[] bomChar = str.toCharArray();// 转为char数组
		char[] noneBomchar = new char[bomChar.length - 1];// 数组第一个元素是bom头，去掉它
		for (int j = 0; j < noneBomchar.length; j++) {
			noneBomchar[j] = bomChar[j + 1];
		}
		return String.valueOf(noneBomchar);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void removeDuplicateWithOrder(List list) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
	}

	public static boolean isNumber(String number) {
		if (StringUtils.isNullOrEmpty(number))
			return false;
		return number.matches("^[-\\+]?[\\d]*$");
	}

	public static boolean isAlpha(String alpha) {
		if (alpha == null)
			return false;
		return alpha.matches("[a-zA-Z]+");
	}

	public static boolean isChinese(String chineseContent) {
		if (chineseContent == null)
			return false;
		return chineseContent.matches("[A-Za-z0-9\\u4e00-\\u9fa5]");
	}

	public static boolean validateNickName(String nickName) {
		boolean flag = false;
		if (isNumber(nickName) && isAlpha(nickName) && isChinese(nickName)) {
			flag = true;
		}
		return flag;
	}

	public static void main(String[] args) {
			
		Date endTime = DateUtil.parse("17:00:00", "H:mm:ss");
		System.out.println(endTime.getTime());
	}

	/**
	 * 获取本机剩余可用内存
	 * 
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static int getAvailableRam() {
		com.sun.management.OperatingSystemMXBean mem = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();
		return (int) (mem.getFreePhysicalMemorySize() / 1024 / 1024);
	}

	/**
	 * 匹配数字
	 * 
	 * @param str
	 * @return
	 */
	public static int findNumber(String str) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return Integer.valueOf(m.replaceAll(""));
	}

	public static String bytes2Hex(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		StringBuilder hex = new StringBuilder();

		for (byte b : bytes) {
			hex.append(HEXES[(b >> 4) & 0x0F]);
			hex.append(HEXES[b & 0x0F]);
			hex.append(" ");
		}

		return hex.toString().toUpperCase();
	}

	/**
	 * 不够位数的在前面补0，保留num的长度位数字
	 * 
	 * @param code
	 * @return
	 */
	public static String autoGenericCode(String code, int num) {
		String result = "";
		result = String.format("%0" + num + "d", Integer.parseInt(code));
		return result;
	}

	/**
	 * 堆栈信息
	 * 
	 * @param stacks
	 */
	public static void printStack(StackTraceElement[] stacks) {
		for (StackTraceElement s : stacks) {
			System.out.println("方法名：dao" + s.getMethodName() + "类名dao：" + s.getClassName() + "行数：" + s.getLineNumber()
					+ "文件名：" + s.getFileName() + "----" + s);
		}
	}

	/**
	 * Java代码实现MySQL数据库导出
	 * 
	 * @param hostIP       MySQL数据库所在服务器地址IP
	 * @param userName     进入数据库所需要的用户名
	 * @param password     进入数据库所需要的密码
	 * @param savePath     数据库导出文件保存路径
	 * @param fileName     数据库导出文件文件名
	 * @param databaseName 要导出的数据库名
	 * @return 返回true表示导出成功，否则返回false。
	 */
	public static boolean exportDatabaseTool(String hostIP, String userName, String password, String savePath,
			String fileName, String databaseName) throws InterruptedException {
		File saveFile = new File(savePath);
		if (!saveFile.exists()) {// 如果目录不存在
			saveFile.mkdirs();// 创建文件夹
		}
		if (!savePath.endsWith(File.separator)) {
			savePath = savePath + File.separator;
		}

		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try {
			printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
			Process process = Runtime.getRuntime().exec(" mysqldump -h" + hostIP + " -u" + userName + " -p" + password
					+ " --set-charset=UTF8 " + databaseName);
			InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
			bufferedReader = new BufferedReader(inputStreamReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				printWriter.println(line);
			}
			printWriter.flush();
			if (process.waitFor() == 0) {// 0 表示线程正常终止。
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static Map<String, Object> objectToMap(Object obj) throws IllegalAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = obj.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			field.setAccessible(true);
			String fieldName = field.getName();
			Object value = field.get(obj);
			map.put(fieldName, value);
		}
		return map;
	}
	
	/**
	 * 随机名字
	 * @return
	 */
	public static String getRandomName() {
		Random random = new Random();
		String ret = "";
		for (int i = 0; i < 3; i++) {
			String str = null;

			int hightPos = 176 + Math.abs(random.nextInt(39));
			int lowPos = 161 + Math.abs(random.nextInt(93));
			byte[] b = new byte[2];
			b[0] = new Integer(hightPos).byteValue();
			b[1] = new Integer(lowPos).byteValue();
			try {
				str = new String(b, "GBK");
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
			ret = ret + str;
		}
		return ret;
	}
	
	/**
	 * unicode转中文
	 * @param ascii
	 * @return
	 */
	public static String unicodeToCh(String ascii) {
		int start = 0;
		int end = 0;
		final StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			end = ascii.indexOf("\\u", start + 2);
			String charStr = "";
			if (end == -1) {
				charStr = ascii.substring(start + 2, ascii.length());
			} else {
				charStr = ascii.substring(start + 2, end);
			}
			char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
			buffer.append(new Character(letter).toString());
			start = end;
		}
		return buffer.toString();
	} 
}