package com.mhx.encryption.service.aes;

import com.mhx.encryption.dto.ResponseVO;
import com.mhx.encryption.dto.symmetry.SymmetryDTO;

/**
 * @interfaceName AesService 
 * @description AES加密算法接口
 * @author MuHongXin.
 * @date 2023/05/31 15:41
 * @version v1.0.0
**/

public interface AesService {
    /**
     * AES加密
     *
     * @author MuHongXin.
     * @dateTime 下午3:42 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return com.mhx.encryption.dto.ResponseVO
    **/
    ResponseVO doAesEncryption(SymmetryDTO symmetryDTO);

    /**
     * AES解密
     *
     * @author MuHongXin.
     * @dateTime 下午3:57 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return com.mhx.encryption.dto.ResponseVO
     **/
    ResponseVO doAesDecryption(SymmetryDTO symmetryDTO);
}
