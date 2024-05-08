package com.mhx.encryption.service.des.impl;

import com.mhx.encryption.dto.ResponseVO;
import com.mhx.encryption.dto.symmetry.SymmetryDTO;
import com.mhx.encryption.service.des.DesService;
import com.mhx.encryption.util.des.DesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @className DesServiceImpl
 * @description DES加密算法接口实现类
 * @author MuHongXin.
 * @date 2023/05/31 16:23
 * @version v1.0.0
 **/

@Service
public class DesServiceImpl implements DesService {
    private static final Logger logger = LoggerFactory.getLogger(DesServiceImpl.class);

    /**
     * DES加密，根据
     *
     *
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     * @author: MuHongXin.
     * @dateTime: 下午4:25 2023/5/31
     **/
    @Override
    public ResponseVO doDesEncryption(SymmetryDTO symmetryDTO) {
        try {
            if (SymmetryDTO.isFieldsNull(symmetryDTO)) {
                return ResponseVO.failed();
            }
        } catch (IllegalAccessException e) {
            logger.info("doDesEncryption|37|abnormal:{}", Arrays.toString(e.getStackTrace()));
        }

        String secretKey = symmetryDTO.getSecretKey();
        String cipherText = "";
        if (null == secretKey || "".equals(secretKey)) {
            try {
                cipherText = DesUtils.encrypt(symmetryDTO.getPlaintext());
            } catch (Exception e) {
                logger.info("doDesEncryption|56|abnormal:{}", Arrays.toString(e.getStackTrace()));
            }
        } else {
            try {
                cipherText = DesUtils.desEncrypt(symmetryDTO.getPlaintext(), secretKey);
            } catch (Exception e) {
                System.out.println("doDesEncryption|54|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        }
        return ResponseVO.ok(cipherText);
    }

    /**
     * DES解密
     *
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     * @author: MuHongXin.
     * @dateTime: 下午4:39 2023/5/31
     **/
    @Override
    public ResponseVO doDesDecryption(SymmetryDTO symmetryDTO) {
        try {
            if (SymmetryDTO.isFieldsNull(symmetryDTO)) {
                return ResponseVO.failed();
            }
        } catch (IllegalAccessException e) {
            System.out.println("doDesDecryption|73|abnormal:" + Arrays.toString(e.getStackTrace()));
        }

        String secretKey = symmetryDTO.getSecretKey();
        String cipherText = "";
        if (null == secretKey || "".equals(secretKey)) {
            try {
                cipherText = DesUtils.decrypt(symmetryDTO.getPlaintext());
            } catch (Exception e) {
                System.out.println("doDesDecryption|82|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        } else {
            try {
                cipherText = DesUtils.desDecrypt(symmetryDTO.getPlaintext(), secretKey);
            } catch (Exception e) {
                System.out.println("doDesDecryption|88|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        }
        return ResponseVO.ok(cipherText);
    }
}
