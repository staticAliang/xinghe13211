package com.fengshen.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtils {

	public static boolean isEmail(String email) {
		try {
			Pattern pattern2 = Pattern.compile(
					"[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+");
			Matcher mat = pattern2.matcher(email);
			return mat.matches();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isMobile(String tel) {
		return Pattern.matches(
				"(134[0-8]\\d{7})|(((13([0-3]|[5-9]))|149|15([0-3]|[5-9])|166|17(3|[5-8])|18[0-9]|19[8-9])\\d{8})",
				tel);
	}

	public static boolean isPhone(String str) {
		Pattern p1 = null;
		Pattern p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$");
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}
}
