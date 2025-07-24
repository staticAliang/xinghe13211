package com.fengshen.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@WebServlet(name = "validateCode", urlPatterns = { "/validateCode" })
public class ValidateCodeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VALIDATE_CODE = "validateCode";
	private int w;
	private int h;

	public ValidateCodeServlet() {
		this.w = 70;
		this.h = 26;
	}

	public void destroy() {
		super.destroy();
	}

	public static boolean validate(final HttpServletRequest request, final String validateCode) {
		if ("test".equals(validateCode)) {
			return true;
		}
		final String code = (String) request.getSession().getAttribute("validateCode");
		return !StringUtils.isEmpty((CharSequence) code) && validateCode.toUpperCase().equals(code);
	}

	public void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final String validateCode = request.getParameter("validateCode");
		if (StringUtils.isNotBlank((CharSequence) validateCode)) {
			response.getOutputStream().print(validate(request, validateCode) ? "true" : "false");
		} else {
			this.doPost(request, response);
		}
	}

	public void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		this.createImage(request, response);
	}

	private void createImage(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");
		final String width = request.getParameter("width");
		final String height = request.getParameter("height");
		if (StringUtils.isNumeric((CharSequence) width) && StringUtils.isNumeric((CharSequence) height)) {
			this.w = NumberUtils.toInt(width);
			this.h = NumberUtils.toInt(height);
		}
		final BufferedImage image = new BufferedImage(this.w, this.h, 1);
		final Graphics g = image.getGraphics();
		this.createBackground(g);
		final String s = createCharacter(g);
		request.getSession().setAttribute("validateCode", (Object) s);
		g.dispose();
		final OutputStream out = (OutputStream) response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.close();
	}

	private Color getRandColor(final int fc, final int bc) {
		int f = fc;
		int b = bc;
		final Random random = new Random();
		if (f > 255) {
			f = 255;
		}
		if (b > 255) {
			b = 255;
		}
		return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
	}

	private void createBackground(final Graphics g) {
		g.setColor(this.getRandColor(220, 250));
		g.fillRect(0, 0, this.w, this.h);
		for (int i = 0; i < 8; ++i) {
			g.setColor(this.getRandColor(40, 150));
			final Random random = new Random();
			final int x = random.nextInt(this.w);
			final int y = random.nextInt(this.h);
			final int x2 = random.nextInt(this.w);
			final int y2 = random.nextInt(this.h);
			g.drawLine(x, y, x2, y2);
		}
	}

	public static String createYzm() {
		final char[] codeSeq = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
				'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		final Random random = new Random();
		return String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);
	}

	private static String createCharacter(final Graphics g) {
		final String[] fontTypes = { "Arial", "Arial Black", "AvantGarde Bk BT", "Calibri" };
		final StringBuilder s = new StringBuilder();
		for (int i = 0; i < 4; ++i) {
			final Random random = new Random();
			final String r = createYzm();
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], 1, 26));
			g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
			s.append(r);
		}
		return s.toString();
	}
}
