package com.mhx.encryption.service.aes.impl;

import com.mhx.encryption.dto.ResponseVO;
import com.mhx.encryption.dto.symmetry.SymmetryDTO;
import com.mhx.encryption.service.aes.AesService;
import com.mhx.encryption.util.aes.AesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @className AesServiceImpl 
 * @description AES加密算法接口实现类 
 * @author MuHongXin. 
 * @date 2023/05/31 15:41
 * @version v1.0.0
**/

@Service
public class AesServiceImpl implements AesService {
    private static final Logger logger = LoggerFactory.getLogger(AesServiceImpl.class);

    /**
     * @param symmetryDTO 对称加密算法对象
     * @return com.mhx.encryption.dto.ResponseVO
     * @author: MuHongXin.
     * @dateTime: 下午3:42 2023/5/31
     * @description: AES加密
     **/
    @Override
    public ResponseVO doAesEncryption(SymmetryDTO symmetryDTO) {
        try {
            if (SymmetryDTO.isFieldsNull(symmetryDTO)) {
                return ResponseVO.failed();
            }
        } catch (IllegalAccessException e) {
            logger.info("doAesEncryption|39|abnormal:" + Arrays.toString(e.getStackTrace()));
        }

        String secretKey = symmetryDTO.getSecretKey();
        String cipherText = "";
        if (null == secretKey || "".equals(secretKey)) {
            try {
                cipherText = AesUtils.encrypt(symmetryDTO.getPlaintext());
            } catch (Exception e) {
                logger.info("doAesEncryption|48|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        } else {
            try {
                cipherText = AesUtils.encrypt(symmetryDTO.getPlaintext(), secretKey);
            } catch (Exception e) {
                logger.info("doAesEncryption|54|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        }

        return ResponseVO.ok(cipherText);
    }

    /**
     * AES解密
     *
     * @param symmetryDTO 对称加密算法对象
     * @return com.mhx.encryption.dto.ResponseVO
     * @author MuHongXin.
     * @dateTime 下午3:57 2023/5/31
     **/
    @Override
    public ResponseVO doAesDecryption(SymmetryDTO symmetryDTO) {
        try {
            if (SymmetryDTO.isFieldsNull(symmetryDTO)) {
                return ResponseVO.failed();
            }
        } catch (IllegalAccessException e) {
            logger.info("doAesEncryption|76|abnormal:" + Arrays.toString(e.getStackTrace()));
        }

        String secretKey = symmetryDTO.getSecretKey();
        String plaintext = "";
        if (null == secretKey || "".equals(secretKey)) {
            try {
                plaintext = AesUtils.decrypt(symmetryDTO.getPlaintext());
            } catch (Exception e) {
                logger.info("doAesEncryption|85|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        } else {
            try {
                plaintext = AesUtils.decrypt(symmetryDTO.getPlaintext(), secretKey);
            } catch (Exception e) {
                logger.info("doAesEncryption|91|abnormal:" + Arrays.toString(e.getStackTrace()));
            }
        }

        return ResponseVO.ok(plaintext);
    }
}
