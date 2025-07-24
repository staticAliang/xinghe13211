package com.fengshen.server.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.fengshen.core.util.Utils;

public class DesUtil
{
    public static String byteArr2HexStr(final byte[] paramArrayOfByte) throws Exception {
        int i = 0;
        final int k = paramArrayOfByte.length;
        final StringBuffer localStringBuffer = new StringBuffer(k * 2);
        while (i < k) {
            int j;
            for (j = paramArrayOfByte[i]; j < 0; j += 256) {}
            if (j < 16) {
                localStringBuffer.append("0");
            }
            localStringBuffer.append(Integer.toString(j, 16));
            ++i;
        }
        return localStringBuffer.toString();
    }
    
    public static String decrypt(String paramString1, final String paramString2) {
        final Key key = getKey(paramString2.getBytes());
        try {
            paramString1 = new String(decrypt(hexStr2ByteArr(paramString1), key));
            return paramString1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static byte[] decrypt(final byte[] paramArrayOfByte, final Key paramKey) throws Exception {
        final Cipher localCipher = Cipher.getInstance("DES");
        localCipher.init(2, paramKey);
        return localCipher.doFinal(paramArrayOfByte);
    }
    
    public static String encrypt(String paramString1, final String paramString2) {
        final Key key = getKey(paramString2.getBytes());
        try {
            paramString1 = byteArr2HexStr(encrypt(paramString1.getBytes(), key));
            return paramString1;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static byte[] encrypt(final byte[] paramArrayOfByte, final Key paramKey) throws Exception {
        final Cipher localCipher = Cipher.getInstance("DES");
        localCipher.init(1, paramKey);
        return localCipher.doFinal(paramArrayOfByte);
    }
    
    private static Key getKey(final byte[] paramArrayOfByte) {
        final byte[] arrayOfByte = new byte[8];
        for (int i = 0; i < paramArrayOfByte.length && i < arrayOfByte.length; ++i) {
            arrayOfByte[i] = paramArrayOfByte[i];
        }
        return new SecretKeySpec(arrayOfByte, "DES");
    }
    
    public static byte[] hexStr2ByteArr(final String paramString) throws Exception {
        final byte[] bytes = paramString.getBytes();
        int i = 0;
        final int j = bytes.length;
        final byte[] arrayOfByte = new byte[j / 2];
        while (i < j) {
            final String str = new String(bytes, i, 2);
            arrayOfByte[i / 2] = (byte)Integer.parseInt(str, 16);
            i += 2;
        }
        return arrayOfByte;
    }
    
    
    public static void main(String[] args) {
    	
    	System.out.println(Utils.bytes2Hex("365284BAC70B481CA02384FEC6A0653E".getBytes()));
    	System.out.println("365284BAC70B481CA02384FEC6A0653E".getBytes().length);
	}
}
