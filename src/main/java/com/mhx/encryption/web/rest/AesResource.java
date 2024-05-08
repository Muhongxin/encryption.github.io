package com.mhx.encryption.web.rest;

import com.mhx.encryption.dto.ResponseVO;
import com.mhx.encryption.dto.symmetry.SymmetryDTO;
import com.mhx.encryption.service.aes.AesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @className EncryptionResource
 * @description AES加密算法入口
 * @author MuHongXin.
 * @date 2023/05/31 15:16
 * @version v1.0.0
**/

@RestController
@RequestMapping("/api/aes")
public class AesResource {
    @Resource
    private AesService aesService;

    /**
     * AES加密
     *
     * @author: MuHongXin.
     * @dateTime: 下午4:20 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
    **/
    @PostMapping("doAesEncryption")
    public ResponseEntity<ResponseVO> doAesEncryption(@RequestBody SymmetryDTO symmetryDTO) {

        return ResponseEntity.ok(this.aesService.doAesEncryption(symmetryDTO));
    }

    /**
     * AES解密
     *
     * @author: MuHongXin.
     * @dateTime: 下午4:20 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     **/
    @PostMapping("doAesDecryption")
    public ResponseEntity<ResponseVO> doAesDecryption(@RequestBody SymmetryDTO symmetryDTO) {

        return ResponseEntity.ok(this.aesService.doAesDecryption(symmetryDTO));
    }
}
