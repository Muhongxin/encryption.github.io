package com.mhx.encryption.util.ecc;

import org.apache.commons.codec.binary.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * @className DesUtils
 * @description DES加密算法工具
 * @author MuHongXin.
 * @date 2022/8/10 下午1:42
 * @version v1.0.0
 **/

public class EccUtil {
    private static final Logger logger = LoggerFactory.getLogger(EccUtil.class);


    private static final String PROVIDER = "BC";


    private static final byte[] PUB_KEY_TL = {'B', 'B', '5', 'D', 'W', 'b', '5', '6', 'J', 'J', 'm', 'p', '4', '4', 'l', 'W', 'l', 'f', '1', 'g', 'F', 'q', 'R', 'o', 'M', 'L', 'M', 'F', '1', 'e', 'H', 'k', 'b', 'g', 'g', '='};


    private static final String EC_IES = "ECIES";


    private static final String EC = "EC";


    private static final String SHA256_WITH_ECDSA = "SHA256withECDSA";

    static {
        logger.info("初始化 ECCUtil 静态资源 | Initialize the ECCUtil static resource");
        if (null == Security.getProvider(PROVIDER)) {
            logger.info("没有找到安全提供程序 | Security provider BC not found..");
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            logger.info("安全提供程序 BC 添加 | Security provider BC add...");
        }
    }

    /**
     * @Description: 获取密钥对
     *               Get a key pair
     * @Author: MuHongXin
     * @DateTime: 下午6:01 2022/8/10
     * @Params: []
     * @Return: java.security.KeyPair
     */
    public static KeyPair genKeyPair() {
        try {
            // 获取 KeyPairGenerator 实例，指定算法为 EC，并指定提供者 | Get a KeyPairGenerator instance, specify the algorithm as EC, and specify the provider
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(EC, PROVIDER);

            // 初始化 KeyPairGenerator，指定密钥长度为 256，并指定随机数生成器 | Initialize the KeyPairGenerator, specify the key length as 256, and specify the random number generator
            keyPairGenerator.initialize(256, new SecureRandom());

            // 生成密钥对并返回 | Generate the key pair and return it
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            logger.info("genKeyPair|72|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    /**
     * @Description: 公钥数组转字符串
     * @Author: MuHongXin
     * @DateTime: 下午6:02 2022/8/10
     * @Params: [publicKey]
     * @Return: java.lang.String
     */
    public static String encodePublicKey(PublicKey publicKey) {
        return StringUtils.newStringUtf8(Base64.encode(publicKey.getEncoded()));
    }

    /**
     * @Description: 解密公钥
     * @Author: MuHongXin
     * @DateTime: 下午6:03 2022/8/10
     * @Params: [keyStr]
     * @Return: java.security.PublicKey
     */
    public static PublicKey decodePublicKey(String keyStr) {
        try {
            // 从公钥字符串中获取公钥 TLV 字节数组 | Get the public key TLV byte array from the public key string
            byte[] keyBytes = getPubKeyTLV(keyStr);

            // 根据公钥 TLV 字节数组生成 X509EncodedKeySpec | Generate X509EncodedKeySpec based on the public key TLV byte array
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            // 获取 KeyFactory 实例，指定算法为 EC，并指定提供者 | Get a KeyFactory instance, specify the algorithm as EC, and specify the provider
            KeyFactory keyFactory = KeyFactory.getInstance(EC, PROVIDER);

            // 根据 X509EncodedKeySpec 生成公钥并返回 | Generate the public key based on X509EncodedKeySpec and return it
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            logger.info("无效的ECC公钥|110|Invalid Ecc public key :{}", Arrays.toString(e.getStackTrace()));
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            logger.info("genKeyPair|112|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * @Description: 密钥长度不足时使用TL数组补充至91位
     * @Author: MuHongXin
     * @DateTime: 下午6:17 2022/8/10
     * @Params: [keyStr]
     * @Return: byte[]
     */
    private static byte[] getPubKeyTLV(String keyStr) {
        // Base64公钥字符串解码
        byte[] keyBytes = Base64.decode(StringUtils.getBytesUtf8(keyStr));

        // 如果公钥长度为65
        if (keyBytes.length == 65) {
            // 阈限值
            byte[] tlv = new byte[91];
            // 从公钥数组下标0开始拷贝26位到tlv数组下标0开始
            System.arraycopy(PUB_KEY_TL, 0, tlv, 0, 26);
            // 从当前公钥数组下标0开始拷贝65位到tlv数组下标26开始，满足91位公钥
            System.arraycopy(keyBytes, 0, tlv, 26, 65);
            return tlv;
        }

        return keyBytes;
    }

    /**
     * @Description: 私钥数组转字符串
     * @Author: MuHongXin
     * @DateTime: 下午6:17 2022/8/10
     * @Params: [privateKey]
     * @Return: java.lang.String
     */
    public static String encodePrivateKey(PrivateKey privateKey) {
        return StringUtils.newStringUtf8(Base64.encode(privateKey.getEncoded()));
    }

    /**
     * @Description: 解码私钥
     * @Author: MuHongXin
     * @DateTime: 下午6:17 2022/8/10
     * @Params: [keyStr]
     * @Return: java.security.PrivateKey
     */
    public static PrivateKey decodePrivateKey(String keyStr) {
        try {
            // 将私钥字符串进行 Base64 解码并得到字节数组 | Decode the private key string in Base64 and get the byte array
            byte[] keyBytes = Base64.decode(StringUtils.getBytesUtf8(keyStr));

            // 根据私钥字节数组生成 PKCS8EncodedKeySpec | Generate PKCS8EncodedKeySpec based on the private key byte array
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);

            // 获取 KeyFactory 实例，指定算法为 EC，并指定提供者 | Get a KeyFactory instance, specify the algorithm as EC, and specify the provider
            KeyFactory keyFactory = KeyFactory.getInstance(EC, PROVIDER);

            // 根据 PKCS8EncodedKeySpec 生成私钥并返回 | Generate the private key based on PKCS8EncodedKeySpec and return it
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            logger.info("无效的ECC私钥|174|Invalid Ecc private key :{}", Arrays.toString(e.getStackTrace()));
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            logger.info("decodePrivateKey|176|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    /**
     * @Description: 公钥加密
     * @Author: MuHongXin
     * @DateTime: 下午6:21 2022/8/10
     * @Params: [content, publicKey]
     * @Return: String
     */
    public static String encrypt(byte[] content, String publicKeyStr) {
        try {
            // 将公钥字符串进行解码得到公钥 | Decode the public key string to get the public key
            PublicKey publicKey = decodePublicKey(publicKeyStr);

            // 获取 Cipher 实例，指定算法为 ECIES，并指定提供者 | Get a Cipher instance, specify the algorithm as ECIES, and specify the provider
            Cipher cipher = Cipher.getInstance(EC_IES, PROVIDER);

            // 初始化 Cipher，指定操作模式为 ENCRYPT_MODE 并使用公钥进行加密 | Initialize the Cipher, specify the operating mode as ENCRYPT_MODE and use the public key for encryption
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            // 对原始数据进行加密 | Encrypt the original data
            byte[] doFinal = cipher.doFinal(content);

            // 将加密后的数据转换为十六进制字符串并返回 | Convert the encrypted data to a hexadecimal string and return it
            return byte2hex(doFinal);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            logger.info("encrypt|206|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    /**
     * @Description: 私钥解密
     * @Author: MuHongXin
     * @DateTime: 下午6:21 2022/8/10
     * @Params: [content, privateKey]
     * @Return: String
     */
    public static String decrypt(byte[] content, String privateKeyStr) {
        try {
            // 将十六进制字符串转换为字节数组 | Convert the hexadecimal string to a byte array
            byte[] bytes = hex2byte(content);

            // 将私钥字符串进行解码得到私钥 | Decode the private key string to get the private key
            PrivateKey privateKey = decodePrivateKey(privateKeyStr);

            // 获取 Cipher 实例，指定算法为 ECIES，并指定提供者 | Get a Cipher instance, specify the algorithm as ECIES, and specify the provider
            Cipher cipher = Cipher.getInstance(EC_IES, PROVIDER);

            // 初始化 Cipher，指定操作模式为 DECRYPT_MODE 并使用私钥进行解密 | Initialize the Cipher, specify the operating mode as DECRYPT_MODE and use the private key for decryption
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // 对加密后的数据进行解密 | Decrypt the encrypted data
            byte[] doFinal = cipher.doFinal(bytes);

            // 将解密后的字节数组转换为字符串并返回 | Convert the decrypted byte array to a string and return it
            return new String(doFinal);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            logger.info("decrypt|204|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    /**
     * @Description: 私钥加签
     * @Author: MuHongXin
     * @DateTime: 下午6:21 2022/8/10
     * @Params: [content, privateKey]
     * @Return: byte[]
     */
    public static byte[] signature(byte[] content, PrivateKey privateKey) {

        try {
            // 获取 Signature 实例，指定签名算法为 SHA256withECDSA | Get a Signature instance, specifying the signature algorithm as SHA256withECDSA
            Signature signature = Signature.getInstance(SHA256_WITH_ECDSA);

            // 初始化 Signature，指定使用私钥进行签名 | Initialize the Signature, specifying the use of private key for signature
            signature.initSign(privateKey);

            // 添加要签名的内容 | Add the content to be signed
            signature.update(content);

            // 完成签名并返回签名结果 | Complete the signature and return the signature result
            return signature.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            logger.info("signature|225|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    /**
     * @Description: 公钥验签
     * @Author: MuHongXin
     * @DateTime: 下午6:22 2022/8/10
     * @Params: [content, sign, publicKey]
     * @Return: boolean
     */
    public static boolean verify(byte[] content, byte[] sign, PublicKey publicKey) {
        try {
            // 获取 Signature 实例，指定签名算法为 SHA256withECDSA | Get a Signature instance, specifying the signature algorithm as SHA256withECDSA
            Signature signature = Signature.getInstance(SHA256_WITH_ECDSA);

            // 初始化 Signature，指定使用公钥进行验签 | Initialize the Signature, specifying the use of public key for signature verification
            signature.initVerify(publicKey);

            // 添加要验签的内容 | Add the content to be verified
            signature.update(content);

            // 验证签名并返回验证结果 | Verify the signature and return the verification result
            return signature.verify(sign);
        } catch (SignatureException | NoSuchAlgorithmException | InvalidKeyException e) {
            logger.info("verify|294|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        return false;
    }

    /**
     * @Description: 16进制转byte
     * @Author: MuHongXin
     * @DateTime: 下午1:37 2022/8/16
     * @Params: [bytes]
     * @Return: String
     */
    private static byte[] hex2byte(byte[] bytes) {
        // 如果字节数组长度不是偶数，则抛出 IllegalArgumentException | If the length of the byte array is not even, throw an IllegalArgumentException
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数 | The length is not even");
        }

        // 创建一个新的字节数组，长度为原字节数组的一半 | Create a new byte array with a length of half the original byte array length
        byte[] b2 = new byte[bytes.length / 2];

        // 循环遍历原字节数组 | Loop through the original byte array
        for (int n = 0; n < bytes.length; n += 2) {

            // 从原字节数组中获取两个字符并转换为字符串 | Get two characters from the original byte array and convert to a string
            String item = new String(bytes, n, 2);

            // 将字符串解析为十六进制数值并存储到新的字节数组中 | Parse the string as a hexadecimal number and store it in the new byte array
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }

        // 返回转换后的新字节数组 | Return the converted new byte array
        return b2;
    }

    /**
     * @Description: byte转16进制数组
     * @Author: MuHongXin
     * @DateTime: 下午1:38 2022/8/16
     * @Params: [bytes]
     * @Return: java.lang.String
     */
    private static String byte2hex(byte[] bytes) {
        // 创建一个 StringBuilder 对象 | Create a StringBuilder object
        StringBuilder hs = new StringBuilder();
        // 定义一个字符串变量 stmp | Define a string variable 'stmp'
        String stmp = "";
        // 遍历字节数组 | Loop through the byte array
        for (byte aByte : bytes) {

            // 将字节转换为十六进制字符串 | Convert the byte to a hexadecimal string
            stmp = String.format("%02X", aByte);

            // 将十六进制字符串添加到 StringBuilder 对象中 | Add the hexadecimal string to the StringBuilder object
            hs.append(stmp);
        }

        // 将 StringBuilder 对象转换为字符串，并将所有字母转换为大写形式，然后返回 | Convert the StringBuilder object to a string, convert all letters to uppercase, and return it
        return hs.toString().toUpperCase();
    }
}
