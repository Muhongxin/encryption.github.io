package com.mhx.encryption.service.des;

import com.mhx.encryption.dto.ResponseVO;
import com.mhx.encryption.dto.symmetry.SymmetryDTO;

/**
 * @interfaceName DesService 
 * @description DES加密算法接口 
 * @author MuHongXin. 
 * @date 2023/05/31 16:22
 * @version v1.0.0
**/

public interface DesService {
    /**
     * DES加密
     *
     * @author: MuHongXin.
     * @dateTime: 下午4:25 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     **/
    ResponseVO doDesEncryption(SymmetryDTO symmetryDTO);

    /**
     * DES解密
     *
     * @author: MuHongXin.
     * @dateTime: 下午4:39 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     **/
    ResponseVO doDesDecryption(SymmetryDTO symmetryDTO);
}
