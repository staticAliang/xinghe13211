package com.fengshen.core.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FieldFilterUtil {

	/**
	 * 过滤字段设置为null
	 * 
	 * @param obj
	 *            指定对象
	 * @param values
	 *            需要过滤的字段
	 */
	public synchronized static void filterField(Object obj, String... values) {

		Class<?> classzz = obj.getClass();
		for (String v : values) {
			try {
				Field f = classzz.getDeclaredField(v);
				f.setAccessible(true);
				f.set(obj, null);
			} catch (NoSuchFieldException e) {
				log.error("{}");
			} catch (SecurityException e) {
				log.error("{}");
			} catch (IllegalArgumentException e) {
				log.error("{}");
			} catch (IllegalAccessException e) {
				log.error("{}");
			}
		}
	}

	/**
	 * 批量过滤字段设置为null
	 * 
	 * @param obj
	 *            指定对象
	 * @param values
	 *            需要过滤的字段
	 */
	public synchronized static void filterField(List<?> obj, String... values) {

		for (Object o : obj) {
			Class<?> classzz = o.getClass();
			for (String v : values) {
				try {
					Field f = classzz.getDeclaredField(v);
					f.setAccessible(true);
					f.set(o, null);
				} catch (NoSuchFieldException e) {
					log.error("{}");
				} catch (SecurityException e) {
					log.error("{}");
				} catch (IllegalArgumentException e) {
					log.error("{}");
				} catch (IllegalAccessException e) {
					log.error("{}");
				}
			}
		}

	}

	/**
	 * 加入需要的字段
	 * 
	 * @param obj
	 *            对象
	 * @param values
	 *            需要加入的字段
	 */
	public synchronized static void includeField(Object obj, String... values) {

		if(obj == null) {
			return;
		}
		Class<?> classzz = obj.getClass();
		Field[] dfs = classzz.getDeclaredFields();
		Map<String, String> data = new HashMap<String, String>();

		for (String v : values) {
			data.put(v, v);
		}
		for (Field f : dfs) {
			f.setAccessible(true);
			String name = f.getName();
			if (data.get(name) == null) {
				try {
					f.set(obj, null);
				} catch (IllegalArgumentException e) {
					log.error("{}");
				} catch (IllegalAccessException e) {
					log.error("{}");
				}
			}
		}
	}

	public synchronized static void includeFields(List<?> objs, String... values) {
		if(objs == null) {
			return;
		}
		for (Object o : objs) {
			Class<?> classzz = o.getClass();
			Field[] dfs = classzz.getDeclaredFields();
			Map<String, String> data = new HashMap<String, String>();
			for (String v : values) {
				data.put(v, v);
			}
			for (Field f : dfs) {
				f.setAccessible(true);
				String name = f.getName();
				if (data.get(name) == null) {
					try {
						f.set(o, null);
					} catch (IllegalArgumentException e) {
						log.error("{}");
					} catch (IllegalAccessException e) {
						log.error("{}");
					}
				}
			}
		}
	}
}
