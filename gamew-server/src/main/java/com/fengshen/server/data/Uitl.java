package com.fengshen.server.data;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class Uitl {
	public static String[] arr;

	public static String scale(final String arr) {
		final long dec_num = Long.parseLong(arr, 16);
		return String.valueOf(dec_num);
	}

	public static void main(final String[] args) throws UnsupportedEncodingException {
	}

	public static void Str() throws UnsupportedEncodingException {
		final String value = String.valueOf("2359323031392d30372d30342031353a30");
		final byte[] bytes = hexToByteArray(value);
		System.out.println(new String(bytes, "GBK"));
		for (int i = 0; i < bytes.length; ++i) {
			final byte aByte = bytes[i];
			System.out.print(aByte + " ");
		}
	}

	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			result = new byte[++hexlen / 2];
			inHex = "0" + inHex;
		} else {
			result = new byte[hexlen / 2];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = hexToByte(inHex.substring(i, i + 2));
			++j;
		}
		return result;
	}

	public static byte hexToByte(final String inHex) {
		return (byte) Integer.parseInt(inHex, 16);
	}

	public static HashMap<Object, Object> kv() {
		final HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
		objectObjectHashMap.put("3014", "police_kill_num");
		objectObjectHashMap.put("3013", "bully_kill_num");
		objectObjectHashMap.put("3012", "server_name");
		objectObjectHashMap.put("3011", "setting/refuse_be_add_level");
		objectObjectHashMap.put("3010", "setting/auto_reply_msg");
		objectObjectHashMap.put("3009", "setting/refuse_stranger_level");
		objectObjectHashMap.put("3004", "group_id");
		objectObjectHashMap.put("3001", "alias");
		objectObjectHashMap.put("3002", "shuadao/chongfeng-san");
		objectObjectHashMap.put("3000", "marriage/marry_id");
		objectObjectHashMap.put("3008", "announcement");
		objectObjectHashMap.put("3005", "leader_gid");
		objectObjectHashMap.put("3006", "member_gid");
		objectObjectHashMap.put("3003", "group_name");
		objectObjectHashMap.put("3007", "setting");
		objectObjectHashMap.put("3017", "gm_attribs/max_mana");
		objectObjectHashMap.put("3018", "gm_attribs/phy_power");
		objectObjectHashMap.put("3024", "chat_floor");
		objectObjectHashMap.put("3020", "gm_attribs/def");
		objectObjectHashMap.put("3015", "show_sandglass");
		objectObjectHashMap.put("3016", "gm_attribs/max_life");
		objectObjectHashMap.put("3021", "gm_attribs/speed");
		objectObjectHashMap.put("3022", "marriage/couple_gid");
		objectObjectHashMap.put("3023", "chat_head");
		objectObjectHashMap.put("3019", "gm_attribs/mag_power");
		objectObjectHashMap.put("3025", "dist_name");
		return objectObjectHashMap;
	}
}
