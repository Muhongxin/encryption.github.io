package com.mhx.encryption.util.des;

import org.thymeleaf.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * DES设计中使用了分组密码设计的两个原则：
 * 混淆（confusion）和扩散(diffusion)，其目的是抗击敌手对密码系统的统计分析。
 * 混淆是使密文的统计特性与密钥的取值之间的关系尽可能复杂化，以使密钥和明文以及密文之间的依赖性对密码分析者来说是无法利用的。
 * 扩散的作用就是将每一位明文的影响尽可能迅速地作用到较多的输出密文位中，以便在大量的密文中消除明文的统计结构，
 * 并且使每一位密钥的影响尽可能迅速地扩展到较多的密文位中，以防对密钥进行逐段破译。
 *
 * Two principles of block cipher design, confusion and diffusion, are used in the design of DES,
 * in order to resist the statistical analysis of the cipher system by the adversary.
 * Obfuscation is to complicate the relationship between the statistical characteristics of ciphertext and the value of the key as much as possible,
 * so that the dependence between the key, plaintext and ciphertext can not be utilized by the cryptanalyst.
 * The purpose of diffusion is to spread the influence of each plaintext bit to more output ciphertext bits as quickly as possible,
 * so as to eliminate the statistical structure of the plaintext in a large number of ciphertext bits,
 * and to spread the influence of each key bit to more ciphertext bits as quickly as possible,
 * so as to prevent the deciphering of the key paragraph-by paragraph.
 */

/**
 * @className DesUtils
 * @description DES加密算法工具
 * @author MuHongXin.
 * @date 2023/05/31 15:40
 * @version v1.0.0
 **/

public class DesUtils {
    /**
     * 偏移量
     *
     * -----------------丨
     * Offset.          丨
     * -----------------丨
     */
    private static final String KEY_IV = "Qh1dd8Wn";

    /**
     * 密钥
     *
     * -----------------丨
     * Secret key.      丨
     * -----------------丨
     */
    private static final String KEY = "2ZpIs8Cj";

    /**
     * 编码集
     *
     * -----------------丨
     * Code set.        丨
     * -----------------丨
     */
    private static final String CHARSET = "utf-8";

    /**
     * 参数分别代表 算法名称/加密模式/数据填充方式
     *
     * --------------------------------------------------------------------------------------------丨
     * The parameters represent the algorithm name, encryption mode, and data filling mode.        丨
     * --------------------------------------------------------------------------------------------丨
     */
    private final static String ALGORITHM = "DES/CBC/PKCS5Padding";

    /**
     * 加密算法 DES
     *
     * --------------------------丨
     * Encryption algorithm DES. 丨
     * --------------------------丨
     */
    private final static String DES = "DES";

    /**
    * @Description: 默认密钥解密
    *              Default key decryption
    * @Author: MuHongXin
    * @DateTime: 下午1:36 2022/8/16
    * @Params: [data]
    * @Return: java.lang.String
    */
    public static String decrypt(String data) {
        if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("加密内容不能为空! | The encryption content cannot be empty!");
        }

        return desDecrypt(new String(hex2byte(data.getBytes())), KEY);
    }

    /**
    * @Description: 默认密钥加密
    *               Default key encryption
    * @Author: MuHongXin
    * @DateTime: 下午1:37 2022/8/16
    * @Params: [data]
    * @Return: java.lang.String
    */
    public static String encrypt(String data) {
        if (StringUtils.isEmpty(data)) {
            throw new IllegalArgumentException("加密内容不能为空! | The encryption content cannot be empty!");
        }

        String encrypt = desEncrypt(data, KEY);
        if (StringUtils.isEmpty(encrypt)) {
            throw new RuntimeException("加密失败! | Encryption failure!");
        }

        return byte2hex(encrypt.getBytes());
    }

    /**
    * @Description: 16进制转byte
    *               Hexadecimal conversion byte
    * @Author: MuHongXin
    * @DateTime: 下午1:37 2022/8/16
    * @Params: [bytes]
    * @Return: byte[]
    */
    private static byte[] hex2byte(byte[] bytes) {
        // 如果字节数组长度不是偶数，则抛出 IllegalArgumentException 异常 | If the length of the byte array is not even, throw an IllegalArgumentException
        if ((bytes.length % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数! | The length is not even!");
        }
        // 创建一个新的 byte 数组用于存储转换后的结果 | Create a new byte array to store the converted result
        byte[] b2 = new byte[bytes.length / 2];
        // 循环遍历字节数组 | Loop through the byte array
        for (int n = 0; n < bytes.length; n += 2) {
            // 将当前的两个字节转换为字符串 | Convert the current two bytes to a string
            String item = new String(bytes, n, 2);
            // 将转换后的字符串解析为十六进制整数，并存储到新的byte数组中 | Parse the converted string as a hexadecimal integer and store it in the new byte array
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }

        // 返回转换后的 byte 数组 | Return the converted byte array
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
        // 创建一个 StringBuilder 实例 | Create a StringBuilder instance
        StringBuilder hs = new StringBuilder();
        // 定义一个空字符串 | Define an empty string
        String stmp = "";
        // 循环遍历字节数组中的每个字节 | Loop through each byte in the byte array
        for (byte aByte : bytes) {
            // 将当前字节转换为十六进制字符串 | Convert the current byte to a hexadecimal string
            stmp = (Integer.toHexString(aByte & 0XFF));
            // 如果当前转换后的字符串长度为1，则在其前面添加一个0 | If the length of the current converted string is 1, add a 0 in front of it
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                // 否则直接将转换后的字符串添加到 StringBuilder 中 | Otherwise, add the converted string directly to the StringBuilder
                hs.append(stmp);
            }
        }

        // 返回转换后的十六进制字符串（字母全部大写）| Return the converted hexadecimal string (all letters in uppercase)
        return hs.toString().toUpperCase();
    }

    /**
     * DES加密
     *
     * @param plaintext 明文
     * @return java.lang.String
     */
    public static String desEncrypt(String plaintext, String key) {
        try {
            // 将密钥转换为 DESKeySpec 类型并指定字符集 | Convert the key to DESKeySpec and specify the character set
            DESKeySpec dks = new DESKeySpec(key.getBytes(CHARSET));
            // 获取 SecretKeyFactory，以便使用指定算法生成SecretKey对象 | Get SecretKeyFactory to generate a SecretKey object using the specified algorithm
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            // 生成 SecretKey 对象 | Generate a SecretKey object
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // 创建用于 CBC 模式的 IV 参数，指定字符集 | Create the IV parameters for CBC mode and specify the character set
            IvParameterSpec ivParameterSpec = new IvParameterSpec(KEY_IV.getBytes(CHARSET));

            // 获取 Cipher 对象，使用指定算法，指定工作模式和填充方式 | Get the Cipher object, specify the algorithm, working mode and padding mode
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化 Cipher 对象，以进行加密操作 | Initialize the Cipher object for encryption
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

            // 对明文进行加密，并将结果转换为 URL 安全的 Base64 编码 | Encrypt the plaintext and convert the result to URL-safe Base64 encoding
            byte[] doFinal = cipher.doFinal(plaintext.getBytes());
            Base64.Encoder encoder = Base64.getUrlEncoder();
            byte[] base64EnStr = encoder.encode(doFinal);

            // 将加密后的字节数组转换为字符串并返回 | Convert the encrypted byte array to a string and return it
            return new String(base64EnStr, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("desEncrypt|139|abnormal:" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException("加密失败! | Encryption failure!");
        }
    }

    /**
     * DES解密
     *
     * @param cipherText 密文
     * @return java.lang.String
     */
    public static String desDecrypt(String cipherText, String key) {
        if (StringUtils.isEmpty(cipherText) || StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("加密内容或秘钥为空! | The encryption content or key is empty!");
        }

        try {
            // 获取 URL 安全的 Base64 解码器 | Get URL-safe Base64 decoder
            Base64.Decoder decoder = Base64.getUrlDecoder();
            // 对密文进行Base64解码 | Base64 decode the cipherText
            byte[] base64DecodeStr = decoder.decode(cipherText);

            // 将密钥转换为 DESKeySpec 类型并指定字符集 | Convert the key to DESKeySpec and specify the character set
            DESKeySpec dks = new DESKeySpec(key.getBytes(CHARSET));
            // 获取 SecretKeyFactory，以便使用指定算法生成SecretKey对象 | Get SecretKeyFactory to generate a SecretKey object using the specified algorithm
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
            // 生成 SecretKey 对象 | Generate a SecretKey object
            SecretKey secretKey = keyFactory.generateSecret(dks);

            // 创建用于 CBC 模式的 IV 参数，指定字符集 | Create the IV parameters for CBC mode and specify the character set
            IvParameterSpec ivParameterSpec = new IvParameterSpec(KEY_IV.getBytes(CHARSET));

            // 获取 Cipher 对象，使用指定算法，指定工作模式和填充方式 | Get the Cipher object, specify the algorithm, working mode and padding mode
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化 Cipher 对象，以进行解密操作 | Initialize the Cipher object for decryption
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

            // 对密文进行解密，并返回解密后的明文 | Decrypt the cipherText and return the decrypted plaintext
            return new String(cipher.doFinal(base64DecodeStr), CHARSET);
        } catch (Exception e) {
            System.out.println("desDecrypt|236|abnormal:" + Arrays.toString(e.getStackTrace()));
            throw new RuntimeException("解密失败! | Decryption failure!");
        }
    }
}
