package com.fengshen.server.util;

public class GameEncryptionUtils {


    public static int bytes4ToIntBig(byte[] src) {
        int offset = 0;
        int value;
        value = (int) (((src[offset] & 0xFF) << 24)
                | ((src[offset + 1] & 0xFF) << 16)
                | ((src[offset + 2] & 0xFF) << 8)
                | (src[offset + 3] & 0xFF));
        return value;
    }

    public static byte[] intToBytes4Big(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    public static byte[] intToBytes4Little(int value) {
        byte[] src = new byte[4];
        src[3] = (byte) ((value >> 24) & 0xFF);
        src[2] = (byte) ((value >> 16) & 0xFF);
        src[1] = (byte) ((value >> 8) & 0xFF);
        src[0] = (byte) (value & 0xFF);
        return src;
    }

    public static int bytes2ToIntBig(byte[] src) {
        int offset = 0;
        int value;
        value = (int) (((src[offset] & 0xFF) << 8)
                | ((src[offset + 1] & 0xFF)));

        return value;
    }

    public static byte[] intToBytes2Big(int value) {
        byte[] src = new byte[2];
        src[0] = (byte) ((value >> 8) & 0xFF);
        src[1] = (byte) (value & 0xFF);
        return src;
    }

    public static String bytesToHexStr(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexStrToBytes(String hexString) {
        hexString = hexString.toUpperCase().replaceAll("\\s+", "");
        int len = hexString.length();
        int index = 0;
        byte[] bytes = new byte[len / 2];

        while (index < len) {

            String sub = hexString.substring(index, index + 2);

            bytes[index / 2] = (byte) Integer.parseInt(sub, 16);

            index += 2;
        }
        return bytes;
    }

    /**
     * 解密出msg code
     * 计算步骤：
     * 0x8C ^ 0xC7 ^ 0x0B = 0x40
     * 0x24 ^ 0x0B = 0x2F
     * @param m 0x00008C24，加密后的msg code
     * @param a 0x80C7000B，sign + body len
     * @return 0x402F
     */
    public static int gfParseM(int m, int a) {
        byte[] m_bytes = intToBytes4Big(m);
        byte[] a_bytes = intToBytes4Big(a);
        byte[] res = new byte[2];
        res[0] = (byte)(m_bytes[2] ^ a_bytes[1] ^ a_bytes[3]);
        res[1] = (byte)(m_bytes[3] ^ a_bytes[3]);

        // return bytesToHexStr(res);
        return bytes2ToIntBig(res);
    }


    /**
     * 解密出key,用这个key解密body
     * 计算步骤：
     * 0xc7 | 0x0b = 0xcf
     * 0x80 | 0x0b = 0x8b
     * 0x80 | 0xc7 = 0xc7
     * 0x80 ^ 0xc7 = 0x47
     * @param m 0x80C7000B
     * @param a 默认值为0x00
     * @return 0xCF8BC747
     */
    public static int gfParseK(int m, int a) {
        byte[] m_bytes = intToBytes4Big(m);
        byte[] res = new byte[4];
        res[0] = (byte) (m_bytes[1] | m_bytes[3]);
        res[1] = (byte) (m_bytes[0] | m_bytes[3]);
        res[2] = (byte) (m_bytes[0] | m_bytes[1]);
        res[3] = (byte) (m_bytes[0] ^ m_bytes[1]);

        return bytes4ToIntBig(res);
    }

    /**
     * 解密数据
     * @param header 抓包数据的前十个字节表示header
     * @param data 抓包数据十字节之后的数据表示data
     * @param out 输出解密后的数据，和data的长度相同。
     * @return -1表示不需要解密，0表示解密成功
     */
    public static int parseHead(byte[] header, byte[] data, byte[] out) {
        byte[] sign_b = new byte[2];
        sign_b[0] = header[2];
        sign_b[1] = header[3];
        int sign = bytes2ToIntBig(sign_b);
        if (sign == 0) { // 不需要解密
            return -1;
        }

        byte[] data_len_b = new byte[2];
        data_len_b[0] = header[8];
        data_len_b[1] = header[9];
        int data_len = bytes2ToIntBig(data_len_b);

        // 解密msg code
        byte[] data_2bytes = new byte[2];
        data_2bytes[0] = data[0];
        data_2bytes[1] = data[1];
        int msg_code = gfParseM(bytes2ToIntBig(data_2bytes), sign << 16 | data_len);
        byte[] msg_code_b = intToBytes2Big(msg_code);
        out[0] = msg_code_b[0];
        out[1] = msg_code_b[1];

        if (data_len == 2) { // data中没有数据，只有一个两字节的msg code
            return 0;
        }

        // 解密body
        int key = gfParseK(sign << 16 | data_len, 0);
        System.out.println(key);
        byte[] key_b = intToBytes4Little(key);
        System.out.println(bytesToHexStr(key_b));
        int len_tmp = (data_len -2) & 0xFFFFFFFC; // 去掉msg code的长度。 截取的长度为4的倍数
        for (int i = 0; i < len_tmp; ++i) {
            out[2+i] = (byte) (data[2+i] ^ key_b[(3 - (i & 3))]);
        }

        if (data_len > len_tmp) { // 处理剩余的数据
            do {
                out[len_tmp+2] = (byte) (data[2+len_tmp] ^ len_tmp);
                ++len_tmp;
            } while (data_len -2 != len_tmp);
        }



        return 0;

    }


    public static byte[] encryptPacket(byte[] header, byte[] body) {

        byte[] sign_b = new byte[2];
        sign_b[0] = header[2];
        sign_b[1] = header[3];
        short sign = (short)bytes2ToIntBig(sign_b);
        int data_len = body.length;
        byte[] msg_code_b = new byte[2]; //{(byte)0xA0, (byte)0xE7};

        msg_code_b[0] = body[0];
        msg_code_b[1] = body[1];
        int a = sign << 16 | data_len;

        byte[] m_bytes = new byte[2];
        byte[] a_bytes = intToBytes4Big(a);
        m_bytes[0] = (byte)(msg_code_b[0] ^ a_bytes[1] ^ a_bytes[3]);
        m_bytes[1] = (byte)(msg_code_b[1] ^ a_bytes[3]);

        body[0] = m_bytes[0];
        body[1] = m_bytes[1];


        int key = gfParseK(a, 0);
        byte[] key_b = intToBytes4Little(key);

        int len_tmp = (data_len - 2) & 0xFFFFFFFC; // 去掉msg code的长度。 截取的长度为4的倍数

        for (int i = 0; i < len_tmp; i++) {
            body[i+2] = (byte) (body[i+2] ^ key_b[(3 - (i & 3))]);
        }

        if (data_len  > len_tmp) { // 处理剩余的数据
            do {
                if (len_tmp + 2 < body.length) {
                    body[len_tmp + 2] = (byte) (body[2 + len_tmp] ^ len_tmp);
                    ++len_tmp;
                }
            } while (data_len -2  != len_tmp);
        }
        return body;
    }

}