package com.mhx.encryption.web.rest;

import com.mhx.encryption.dto.ResponseVO;
import com.mhx.encryption.dto.symmetry.SymmetryDTO;
import com.mhx.encryption.service.des.DesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @className DesResource 
 * @description DES加密算法入口
 * @author MuHongXin. 
 * @date 2023/05/31 16:21
 * @version v1.0.0
**/

@RestController
@RequestMapping("/api/des")
public class DesResource {
    @Resource
    private DesService desService;

    /**
     * DES加密
     *
     * @author: MuHongXin.
     * @dateTime: 下午4:20 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     **/
    @PostMapping("doDesEncryption")
    public ResponseEntity<ResponseVO> doDesEncryption(@RequestBody SymmetryDTO symmetryDTO) {

        return ResponseEntity.ok(this.desService.doDesEncryption(symmetryDTO));
    }

    /**
     * DES解密
     *
     * @author: MuHongXin.
     * @dateTime: 下午4:20 2023/5/31
     * @param symmetryDTO 对称加密算法对象
     * @return org.springframework.http.ResponseEntity<com.mhx.encryption.dto.ResponseVO>
     **/
    @PostMapping("doDesDecryption")
    public ResponseEntity<ResponseVO> doDesDecryption(@RequestBody SymmetryDTO symmetryDTO) {

        return ResponseEntity.ok(this.desService.doDesDecryption(symmetryDTO));
    }
}
