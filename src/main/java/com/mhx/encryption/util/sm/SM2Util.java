package com.mhx.encryption.util.sm;

import com.alibaba.fastjson.JSON;
import com.mhx.encryption.common.ErrorCodeEnum;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author MuHongXin.
 * @Description: 国密SM2工具包
 * @data 2022/8/17 下午4:29
 */

public class SM2Util {
    private static final Logger logger = LoggerFactory.getLogger(SM2Util.class);

    //生成随机秘钥对  
    public static SM2KeyPair generateKeyPair() {
        SM2 sm2 = SM2.instance();

        AsymmetricCipherKeyPair key = sm2.eccKeyPairGenerator.generateKeyPair();
        ECPrivateKeyParameters ecPrivateKeyParameters = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecPublicKeyParameters = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecPrivateKeyParameters.getD();
        ECPoint publicKey = ecPublicKeyParameters.getQ();

        return new SM2KeyPair(DataConversionUtils.byteToHex(publicKey.getEncoded()), DataConversionUtils.byteToHex(privateKey.toByteArray()));
    }

    public static <T> T JSON2Entity(String data, Class<T> clazz) {
        logger.info("JSON2Entity|99|data:{}, clazz:{}", data, clazz);
        if (StringUtils.isEmpty(data)) {
            throw new RuntimeException(ErrorCodeEnum.SIGN_EXCEPTION.getMsg());
        }

        T t = null;
        try {
            t = JSON.parseObject(data, clazz);
        } catch (Exception e) {
            logger.info("JSON2Entity|108|出现异常:{}", Arrays.toString(e.getStackTrace()));
        }
        return t;
    }

    /**
    * @Description: 加密
    * @Author: MuHongXin
    * @DateTime: 下午6:25 2022/8/17
    * @Params: [publicKey, data]
    * @Return: java.lang.String
    */
    public static String encrypt(String publicKey, String data) {
        if (StringUtils.isEmpty(publicKey) || StringUtils.isEmpty(data)) {
            return null;
        }
        byte[] publicKeyBytes = DataConversionUtils.hexToByte(publicKey);
        byte[] dataBytes = data.getBytes();
        byte[] source = new byte[dataBytes.length];
        System.arraycopy(dataBytes, 0, source, 0, dataBytes.length);

        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.instance();
        ECPoint userKey = sm2.eccCurve.decodePoint(publicKeyBytes);

        ECPoint c1 = cipher.initEnc(sm2, userKey);
        cipher.encrypt(source);
        byte[] c3 = new byte[32];
        cipher.doFinal(c3);

        //C1 C2 C3拼装成加密字串
        return DataConversionUtils.byteToHex(c1.getEncoded()) + DataConversionUtils.byteToHex(source) + DataConversionUtils.byteToHex(c3);
    }

    /**
    * @Description: 解密
    * @Author: MuHongXin
    * @DateTime: 下午6:25 2022/8/17
    * @Params: [privateKey, encryptedData]
    * @Return: byte[]
    */
    public static String decrypt(String privateKey, String encryptedData) {
        if (StringUtils.isEmpty(privateKey) || StringUtils.isEmpty(encryptedData)) {
            return null;
        }

        byte[] privateBytes = DataConversionUtils.hexToByte(privateKey);
        byte[] encryptedDataBytes = DataConversionUtils.hexToByte(encryptedData);

        //加密字节数组转换为十六进制的字符串 长度变为encryptedData.length * 2  
        String data = DataConversionUtils.byteToHex(encryptedDataBytes);
        /**
         * 分解加密字串
         * （C1 = C1标志位2位 + C1实体部分128位 = 130） 
         * （C3 = C3实体部分64位  = 64） 
         * （C2 = encryptedData.length * 2 - C1长度  - C2长度） 
         */
        byte[] c1Bytes = DataConversionUtils.hexToByte(data.substring(0, 130));
        int c2Len = encryptedDataBytes.length - 97;
        byte[] c2 = DataConversionUtils.hexToByte(data.substring(130, 130 + 2 * c2Len));
        byte[] c3 = DataConversionUtils.hexToByte(data.substring(130 + 2 * c2Len, 194 + 2 * c2Len));

        SM2 sm2 = SM2.instance();
        BigInteger userD = new BigInteger(1, privateBytes);

        //通过C1实体字节来生成ECPoint  
        ECPoint c1 = sm2.eccCurve.decodePoint(c1Bytes);
        Cipher cipher = new Cipher();
        cipher.initDec(userD, c1);
        cipher.decrypt(c2);
        cipher.doFinal(c3);

        //返回解密结果  
        return new String(c2, StandardCharsets.UTF_8);
    }

//    public static void main(String[] args) throws Exception {
//        // 生成密钥对
//        SM2KeyPair sm2KeyPair = generateKeyPair();
//        System.out.println("公钥：" + sm2KeyPair.getPublicKey());
//        System.out.println("私钥：" + sm2KeyPair.getPrivateKey());
//
////        LoginAccountDTO loginAccountDTO = new LoginAccountDTO();
////        loginAccountDTO.setAppId("high172200");
////        loginAccountDTO.setAppSecret("guoguo2233");
//
//        CdKeyOutboundRequestDTO cdKeyOutboundRequestDTO = new CdKeyOutboundRequestDTO();
//        cdKeyOutboundRequestDTO.setOutboundNum(10L);
//        cdKeyOutboundRequestDTO.setOutboundType(new Byte("0"));
//        cdKeyOutboundRequestDTO.setActivityCode("AC202201010090");
//        cdKeyOutboundRequestDTO.setSceneCode("scene_wm");
//        String jsonString = JSON.toJSONString(cdKeyOutboundRequestDTO);
//
//        // 公钥
////        String publicKey = sm2KeyPair.getPublicKey();
//        // 私钥
////        String privateKey = sm2KeyPair.getPrivateKey();
//        String publicKey = "04B73F8D6CAC57E71F56322E998356993B5EA3FDDFD64513486FDAB062714E9C119369B6D713506C9714190E50E49EF92C4981B438D79F96C139AB9B71EC7331D9";
//        String privateKey = "00DF285E8EA0D37EB44480FFEB593491DEE5CB6CA18E1F9E43C176CBDB7E3A5466";
//
//        long encryptStart = System.currentTimeMillis();
//        // 加密
//        String cipherText = SM2Util.encrypt(publicKey, "qy5Q-ZLNT-MOHo-fBgW");
//        System.out.println("加密用时：" + (System.currentTimeMillis() - encryptStart));
//        System.out.println("加密: ");
//        System.out.println(cipherText);
//
//        long decryptStart = System.currentTimeMillis();
//        // 解密
//        String decryptStr = SM2Util.decrypt(privateKey, cipherText);
//        System.out.println("解密用时：" + (System.currentTimeMillis() - decryptStart));
//        System.out.println("解密: ");
//        System.out.println(decryptStr);
////        LoginAccountDTO parseObject = JSONObject.parseObject(decryptStr, LoginAccountDTO.class);
////        System.out.println(parseObject.getAppId());
//    }
}
