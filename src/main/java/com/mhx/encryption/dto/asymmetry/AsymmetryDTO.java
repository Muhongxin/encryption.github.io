package com.mhx.encryption.dto.asymmetry;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @className AsymmetryDTO 
 * @description 非对称加密算法对象
 *              Asymmetric encryption algorithm object
 * @author MuHongXin. 
 * @date 2023/05/31 15:28
 * @version v1.0.0
**/

public class AsymmetryDTO {
    /**
     * 明文
     */
    private String plaintext;

    /**
     * 密文
     */
    private String cipherText;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 对象类型
     * 0 - 加密 encryption
     * 1 - 解密 decryption
     */
    private static final Byte dtoType = 0;

    public AsymmetryDTO() {
    }

    public AsymmetryDTO(String plaintext, String cipherText, String publicKey, String privateKey) {
        this.plaintext = plaintext;
        this.cipherText = cipherText;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPlaintext() {
        return plaintext;
    }

    public void setPlaintext(String plaintext) {
        this.plaintext = plaintext;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AsymmetryDTO that = (AsymmetryDTO) o;
        return Objects.equals(plaintext, that.plaintext) &&
                Objects.equals(cipherText, that.cipherText) &&
                Objects.equals(publicKey, that.publicKey) &&
                Objects.equals(privateKey, that.privateKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plaintext, cipherText, publicKey, privateKey);
    }

    @Override
    public String toString() {
        return "AsymmetryDTO{" +
                "plaintext='" + plaintext + '\'' +
                ", cipherText='" + cipherText + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }

    public static boolean isFieldsNull(Object obj) throws IllegalAccessException {
        if (obj == null) {
            return true;
        }

        Class<?> clazz = obj.getClass();
        // 获取对象中的所有成员变量
        Field[] fields = clazz.getDeclaredFields();
        // 设置成员变量可访问
        AccessibleObject.setAccessible(fields, true);
        // 判断每个成员变量是否为 null
        for (Field field : fields) {
            if (field.get(obj) == null) {
                return true;
            }
        }

        return false;
    }
}
