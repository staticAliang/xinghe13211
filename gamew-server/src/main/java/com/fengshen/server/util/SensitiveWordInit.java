package com.fengshen.server.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fengshen.server.domain.config.Mingan;
import com.google.common.collect.Lists;

/**
 * a初始化mganci
 * 
 *
 */
public class SensitiveWordInit {

	@SuppressWarnings("rawtypes")
	public static HashMap sensitiveWordMap;

	public SensitiveWordInit() {
		super();
	}

	/**
	 * 初始化词库
	 * 
	 * @param datas 敏感词集合
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap init(List<String> datas) {
		addSensitiveWord(datas);
		return sensitiveWordMap;
	}

	/**
	 * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void addSensitiveWord(List<String> datas) {
		sensitiveWordMap = new HashMap(datas.size());
		Iterator<String> iterator = datas.iterator();
		Map<String, Object> now = null;
		Map now2 = null;
		while (iterator.hasNext()) {
			now2 = sensitiveWordMap;
			String word = iterator.next().trim(); // 敏感词
			for (int i = 0; i < word.length(); i++) {
				char key_word = word.charAt(i);
				Object obj = now2.get(key_word);
				if (obj != null) { // 存在
					now2 = (Map) obj;
				} else { // 不存在
					now = new HashMap<String, Object>();
					now.put("isEnd", "0");
					now2.put(key_word, now);
					now2 = now;
				}
				if (i == word.length() - 1) {
					now2.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * a获取内容中的敏感词
	 * 
	 * @param text      内容
	 * @param matchType 匹配规则 1=不最佳匹配，2=最佳匹配
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<String> getSensitiveWord(String text, int matchType) {
		List<String> words = new ArrayList<String>();
		Map now = sensitiveWordMap;
		int count = 0; // 初始化敏感词长度
		int start = 0; // 标志敏感词开始的下标
		for (int i = 0; i < text.length(); i++) {
			char key = text.charAt(i);
			now = (Map) now.get(key);
			if (now != null) { // 存在
				count++;
				if (count == 1) {
					start = i;
				}
				if ("1".equals(now.get("isEnd"))) { // 敏感词结束
					now = sensitiveWordMap; // 重新获取敏感词库
					words.add(text.substring(start, start + count)); // 取出敏感词，添加到集合
					count = 0; // 初始化敏感词长度
				}
			} else { // 不存在
				now = sensitiveWordMap;// 重新获取敏感词库
				if (count == 1 && matchType == 1) { // 不最佳匹配
					count = 0;
				} else if (count == 1 && matchType == 2) { // 最佳匹配
					words.add(text.substring(start, start + count));
					count = 0;
				}
			}
		}
		return words;
	}

	/**
	 * a从文件中读取词库中的内容，将内容添加到list集合中
	 * @return
	 */
	public static List<String> readSensitiveWord() {
		Mingan mingan = GameConfig.config.getMingan();
		String filterText = mingan.getSettings().getFilterText();
		String[] split = filterText.split("、");
		return Lists.newArrayList(split);
	}

}