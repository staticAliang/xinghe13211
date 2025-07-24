package com.fengshen.server.util;

public class CompeteTournamentUtils {

	private final static int x1 = 161;
	private final static int y1 = 86;

	@SuppressWarnings("unused")
	private final static int x2 = 185;
	@SuppressWarnings("unused")
	private final static int y2 = 72;

	private final static int x3 = 185;
	private final static int y3 = 98;

	private final static int x4 = 209;
	private final static int y4 = 84;

	// 是否在擂台上
	public static boolean onLeitai(int x, int y) {
		return isInside(x, y);
	}

	// 判断一个点是否在矩形内部
	public static boolean isInside(double x1, double y1, double x4, double y4, double x, double y) {
		// 默认:1点在左上,4点在右下
		if (x <= x1) {// 在矩形左侧
			return false;
		}
		if (x >= x4) {// 在矩形右侧
			return false;
		}
		if (y >= y1) {// 在矩形上侧
			return false;
		}
		if (y <= y4) {// 在矩形下侧
			return false;
		}
		return true;
	}

	public static boolean isInside(double x, double y) {

		// 坐标变换，把矩形转成平行，所有点跟着动
		double a = Math.abs(y4 - y3);
		double b = Math.abs(x4 - x3);
		double c = Math.sqrt(a * a + b * b);
		double sin = a / c;
		double cos = b / c;

		double x11 = cos * x1 + sin * y1;
		double y11 = -x1 * sin + y1 * cos;
		double x44 = cos * x4 + sin * y4;
		double y44 = -x4 * sin + y4 * cos;
		double xx = cos * x + sin * y;
		double yy = -x * sin + y * cos;
		// 旋转完成，又变成上面一种平行的情况
		return isInside(x11, y11, x44, y44, xx, yy);
	}
}
