package com.fengshen.server.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SensitivewordFilter {
	@SuppressWarnings("rawtypes")
	private static Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1; // 最小匹配规则
	public static int maxMatchType = 2; // 最大匹配规则

	public static void initSensitiveWord(List<String> datas) {
		sensitiveWordMap = SensitiveWordInit.init(datas);
	}

	/**
	 * a替换敏感字字符
	 * 
	 * @date 2014年4月20日 下午5:12:07
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @version 1.0
	 */
	public static String replaceSensitiveWord(List<String> datas, String txt, int matchType, String replaceChar) {
		if (sensitiveWordMap == null) {
			initSensitiveWord(datas);
		}
		String resultTxt = txt;
		Iterator<String> iterator = datas.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * a获取与词库中相匹配的词语
	 * 
	 * @date 2014年4月20日 下午5:12:07
	 * @param txt
	 * @param matchType
	 * @param replaceChar 替换字符，默认*
	 * @version 1.0
	 */
	public static List<String> getSensitiveWord(List<String> datas, String txt, int matchType) {
		if (sensitiveWordMap == null) {
			initSensitiveWord(datas);
		}
		List<String> list = SensitiveWordInit.getSensitiveWord(txt, matchType); // 获取所有的敏感词
		return list;
	}

	/**
	 * a添加敏感词
	 * 
	 * @param str
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean addSensitiveWord(String str) {
		if (sensitiveWordMap == null) {
			return false;
		}
		Map nowMap = null;
		Map<String, String> newWorMap = null;
		nowMap = sensitiveWordMap;
		for (int i = 0; i < str.length(); i++) {
			char keyChar = str.charAt(i); // 转换成char型
			Object wordMap = nowMap.get(keyChar); // 获取
			if (wordMap != null) { // 如果存在该key，直接赋值
				nowMap = (Map) wordMap;
			} else { // 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
				newWorMap = new HashMap<String, String>();
				newWorMap.put("isEnd", "0"); // 不是最后一个
				nowMap.put(keyChar, newWorMap);
				nowMap = newWorMap;
			}
			if (i == str.length() - 1) {
				nowMap.put("isEnd", "1"); // 最后一个
			}
		}
		return true;
	}

	/**
	 * a 获取替换字符串
	 * 
	 * @author chenming
	 * @date 2014年4月20日 下午5:21:19
	 * @param replaceChar
	 * @param length
	 * @return
	 * @version 1.0
	 */
	private static String getReplaceChars(String replaceChar, int length) {
		String resultReplace = replaceChar;
		if (length > 6) {
			length = 6;
		}
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}
		return resultReplace;
	}

	/**
	 * a重新刷新配置
	 * @param value
	 */
	public static void refresh(List<String> value) {
		sensitiveWordMap = SensitiveWordInit.init(value);
	}

	 public static void main(String[] args) throws Exception {
		 //从文件中读取词库中的内容，将内容添加到list集合中
		 List<String> datas = SensitiveWordInit.readSensitiveWord();
		 System.out.println(datas);
		 String str = "很卡a啊很卡"; 
		 String rep = SensitivewordFilter.replaceSensitiveWord(datas,str,1,"*"); 
		 System.out.println(rep); 
		 
	}
}