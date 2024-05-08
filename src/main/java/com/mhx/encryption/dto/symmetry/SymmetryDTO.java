package com.mhx.encryption.dto.symmetry;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @className SymmetryDTO 
 * @description 对称加密算法对象
 *              Symmetric encryption algorithm object
 * @author MuHongXin. 
 * @date 2023/05/31 15:27
 * @version v1.0.0
**/

public class SymmetryDTO {
    /**
     * 明文
     */
    private String plaintext;

    /**
     * 密文
     */
    private String cipherText;

    /**
     * 密钥
     */
    private String secretKey;

    public SymmetryDTO() {
    }

    public SymmetryDTO(String plaintext, String cipherText, String secretKey) {
        this.plaintext = plaintext;
        this.cipherText = cipherText;
        this.secretKey = secretKey;
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SymmetryDTO that = (SymmetryDTO) o;
        return Objects.equals(plaintext, that.plaintext) &&
                Objects.equals(cipherText, that.cipherText) &&
                Objects.equals(secretKey, that.secretKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plaintext, cipherText, secretKey);
    }

    @Override
    public String toString() {
        return "SymmetryDTO{" +
                "plaintext='" + plaintext + '\'' +
                ", cipherText='" + cipherText + '\'' +
                ", secretKey='" + secretKey + '\'' +
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
