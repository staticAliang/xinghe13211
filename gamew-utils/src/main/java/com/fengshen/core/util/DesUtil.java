package com.fengshen.core.util;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import org.bouncycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi.MD5;

import com.qiniu.util.Md5;

import java.io.*;

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

    public static String decrypt(String value, final String paramString2) {
        final Key key = getKey(paramString2.getBytes());
        try {
        	value = new String(decrypt(hexStr2ByteArr(value), key));
            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    // 解密
    public static byte[] decrypt(final byte[] paramArrayOfByte, final Key paramKey) throws Exception {
        final Cipher localCipher = Cipher.getInstance("DES");
        localCipher.init(2, paramKey);
        return localCipher.doFinal(paramArrayOfByte);
    }
    // 加密
    public static String encrypt(String value, final String paramString2) {
        final Key key = getKey(paramString2.getBytes());
        try {
        	value = byteArr2HexStr(encrypt(value.getBytes(), key));
            return value;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
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

    public static void main(final String[] args) throws Exception {
        final String decrypt = decrypt("662a14bd16bef3c6d5ab3e907fafed5c1e01123bf37248a597c039ee3b0a198a2b314ad68e93ba170d9e406d0350e4b990a4380a3610e8df78f1bdb966d1b4d9d4cc25b4560a9145b15b7a6f1e08122fc6b8b90ff0ef14196cffe8eb2f55e02c7790119f05ec1fe452ea47d43eab8065dde47bd41a95b908b93faef29e5685478c5cc638b2cc79d2948b45928d891ff9ad46e44adc2987ec8e67ec4cb5f225c704f926b6e409def1534e9e3b87fcd025d5ab3e907fafed5cc6d827eed9344e1379de35c7d3499cabf0a8ca4595d315dac8fb5a2031443b7cd5ab3e907fafed5c317f0503640868ad587b1a7fcf6fdf338d99db6cd8abe4a2c3016f202a49967c82fdfb6e84c9d1c11ed8f875b89b58b28c907508e93460f30c54e40de258a8e22adf8c52f98080de6d19bbf416bb92b29bf3d22bc9dca11282529a1752cf8cb997aa8416e64d11a804b067e93af0fc4e43d5021d2ebdcdbfe126c0b8d7285629cedbee3a5ba4bd4f0e1069d4e7c6e2d172354de21c4889875df47c01c7aa34bfb42733dfa2c1778ccb659a3eb3d48253fb4ddec2c123c60df35d89d6dce48c2d4d717460108b371811270768f13e26a782529a1752cf8cb90fc95175e0c5246b693d2dad52d020e129ee606502044b3fa31a67ebb329f4601044a21099fb6a705bf68e263ba04b21854c75a948965226cc2f5d8b628c22beab25051ffec930d990d3459c1387b245ed49935d2eafced52d5866b98bdc7ee921bb461a704d8e179fcf3833c0fbc86c6f8d9244c2238593b72219aa86202bbb6ccac83dbda555c07d6fdfe91dde23bb9ad9429381e6ffddc0b8f6a95fcf64c8e3f24ae94328c8bc4420cbcd5ffd72aaed572d4270c8d2321d4dfd2316aaaf3616fb77cd41613d038e01e291d9493a9b0e0a8f31f83a992d74cd293f62781c5df87db45b96922559428047108128af80e67e24aefbebb746b224b19bc09e4f2800e78bae33cec92ae47e04c6b61e48547573f5a010e8da26175b8fdefb8c6903b9d7efa272f5bcda7954a8b0cfb5f56aac7beea28b526030b25ea2686e2664b50e39ad0b8743bc4474cd293f62781c5d120e4f26a13e2b075bd5a64f52f36340142468f418d65789fcf1fc1c9df5c55af8bbc0bd556df8ba5b380a4ad95f811234d8c2402c453ba4e7ee7ce90f5b43ae83ceb2a4daa787142d55c8fe960e49978baf683501c619786cb43e873e2f7113b6661f981dc80c60131164faa40b6ed36f9b09b4162ff2b1f49b74bae499d0e1dbc7ebeacbcf687d096fde4bd29f460352ae0ee21c68b754c5d2f85374f3611c22241a1732a7c87f354e8b481ef5952e8493ea1907c0db86bd053ea9d926c47074cd293f62781c5dc8a3787bff6cf9abebc2ccce7ce7f4aa388118f1ee4c306332512246b5e2f49ea49b5114fed9035aa4a911467c4ad0a06b48e824d67fc53057be42c1ad289fd0ffd07c8af8ce38da8624b1c9972e0fed06e972e121576bc555b4dbbb2944e7dbab25051ffec930d94b8116df8f919a71b5764195ce3ea0d29b12c8985e143ca61858d59cdf49aa1e604e7718ca72880986b5576dca424d0b7272d703c4ab7d6f3610b2d854f29e03b3b49b64946bfb93cf91159787d402495d8dd201c32d2cca6a955c736aaaf1398e61bc972d5f0e74c7fc2254931ea11f6e8ca59214f0edd687eb70fa1ca46ab086d4329c02f969368e1cff450f063be720f3cb056056f68203a0639c44a00c5c7b78af283b33c9a7438275265cdb225f4a3de82fbab087af509ce42f62a7948beb832eec8916b1f3c1bd00ef0925bf2783a62632195d85fe708a5377bc29afb2b3d8d520d76e07f86a28c74e18e8256b32fdf98debf86640ba6275bca30976209fe0b3c5f6a64686ae47313e43199556ba4a27b0230c65807b15e022d6dbbf80da54516ba2c8f3d0", "548711fdc20a2129");
        System.out.println(decrypt);
        final String encrypt = encrypt("{\"loginUrl\":\"http://192.168.0.88:81/vip4\",\"payUrl\":\"http://192.168.0.88:81/vip4\",\"accountUrl\":\"http://192.168.0.88:81ios/vip4\",\"bbsUrl\":\"http://bbs.leiting.com\",\"helpUrl\":\"http://helper.leiting.com\",\"kernelUrl\":\"http://www.leiting.com\",\"logUrl\":\"http://tplog.leiting.com\",\"urlApis\":[{\"name\":\"login\",\"value\":\"/login/login.php\"},{\"name\":\"checkLogin\",\"value\":\"/login/mobile!mobileCheckLoginV3.php\"},{\"name\":\"fastRegister\",\"value\":\"/login/mobile!fastRegisterV2.action\"},{\"name\":\"payOrder\",\"value\":\"/terrace/phone_charge!createLeitingNo.action\"},{\"name\":\"getAdultInfo\",\"value\":\"/terrace/game_api/getIdCardBindInfo.htm\"}],\"channels\":[{\"name\":\"leiting\",\"value\":\"{\\\"guestLogin\\\":\\\"0\\\",\\\"payLevel\\\":\\\"1\\\",\\\"gmPhoneNum\\\":\\\"0592-3011618\\\"}\"}],\"plugs\":[{\"name\":\"qiyukf\",\"value\":\"{\\\"appKey\\\":\\\"b8c33a8308b536dc4fde7be133fc9835\\\",\\\"groupId\\\":\\\"354781\\\",\\\"robotId\\\":\\\"83373\\\"}\"},{\"name\":\"toutiao\",\"value\":\"{\\\"appId\\\":\\\"151304\\\",\\\"appName\\\":\\\"wendao\\\"}\"},{\"name\":\"wechath5\",\"value\":\"{\\\"payResult\\\":\\\"/terrace/phone_charge!searchResultByLeitingNo.action\\\",\\\"payResultChannel\\\":\\\"/terrace/notify_back!searchResultByLeitingNo.action\\\",\\\"payLoginResult\\\":\\\"/terrace/drm_charge!queryWechatPayResult.action\\\"}\"}],\"resValues\":[{\"name\":\"lt_realname_auth_toast_msg1\",\"value\":\"根据国家法规要求，您的账号信息尚未完善，请尽快完成实名认证以保障账号安全。\"}]}", "548711fdc20a2129");
        System.out.println(encrypt);
    }
}