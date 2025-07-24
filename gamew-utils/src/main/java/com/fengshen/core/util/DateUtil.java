package com.fengshen.core.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间格式化
 */
public class DateUtil {

	/** 锁对象 */
	private static final Object lockObj = new Object();

	/** 存放不同的日期模板格式的sdf的Map */
	private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	/**
	 * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
	 * 
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);

		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}

		return tl.get();
	}

	/**
	 * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return getSdf(pattern).format(date);
	}

	public static Date parse(String dateStr, String pattern) {
		try {
			return getSdf(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取两个时间相差多少毫秒
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getMin(String start, String end) {
		SimpleDateFormat sdf = getSdf("yyyy-MM-dd");
		start = sdf.format(new Date()) + " " + start + ":" + "00";
		end = sdf.format(new Date()) + " " + end + ":" + "00";
		SimpleDateFormat sdf2 = getSdf("yyyy-MM-dd HH:mm:ss");
		long min = 0;
		try {
			min = sdf2.parse(end).getTime() - sdf2.parse(start).getTime();

		} catch (ParseException e) {
		}
		return min;
	}


	/**
	 * 获取过去第几天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static Date getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		return today;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static Date getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		return today;
	}
	
	/**
	 * a获取过去第几天的日期
	 * @param date 时间
	 * @param past
	 * @return
	 */
	public static Date getPastDate(Date date, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		return today;
	}

	/**
	 * a获取未来 第 past 天的日期
	 * @param date 时间
	 * @param past
	 * @return
	 */
	public static Date getFetureDate(Date date, int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		return today;
	}

	public static void main(String[] args) throws Exception {
		String format = "HH:mm:ss";
		Date startTime = new SimpleDateFormat(format).parse("20:00:00");
		Date endTime = new SimpleDateFormat(format).parse("22:15:00");
		System.out.println(isEffectiveDate(startTime, endTime));
	}

	/**
	 * a获取网络时间
	 * @return
	 */
	public static Date getWebsiteDatetime() {
		try {
			URL url = new URL("https://www.baidu.com");// 取得资源对象
			URLConnection uc = url.openConnection();// 生成连接对象
			uc.connect();// 发出连接
			long ld = uc.getDate();// 读取网站日期时间
			Date date = new Date(ld);// 转换为标准时间对象
			return date;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 /**
     * 判断当前时间是否在[startTime, endTime]区间
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    public static boolean isEffectiveDate(Date startTime, Date endTime) {
    	Date nowTime = DateUtil.parse(DateUtil.format(new Date(), "HH:mm:ss"), "HH:mm:ss");
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}