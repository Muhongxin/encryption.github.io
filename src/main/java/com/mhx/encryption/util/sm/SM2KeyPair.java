package com.mhx.encryption.util.sm;


/**
 * @author MuHongXin.
 * @Description: 国密SM2密钥
 * @data 2022/8/17 下午4:31
 */

public class SM2KeyPair {
    /**
     * 公钥/私钥
     */
    private String publicKey, privateKey;

    public SM2KeyPair(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }
}
