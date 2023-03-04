package com.me.yimu.pay.merchant.service.impl;

import java.sql.BatchUpdateException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.CommonErrorCode;
import com.me.yimu.pay.merchant.service.FileService;
import com.me.yimu.pay.utils.QiniuUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 文件处理服务类
 * @author PC
 *
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    // 配置参数
    @Value("${oss.qiniu.url}")
    private String qiniuUrl;
    @Value("${oss.qiniu.accessKey}")
    private String accessKey;
    @Value("${oss.qiniu.secretKey}")
    private String secretKey;
    @Value("${oss.qiniu.bucket}")
    private String bucket;
    
    @Override
    public String upload(byte[] bytes, String fileName) throws BatchUpdateException {
        log.info("FileServiceImpl.upload start");
        
        try {
            QiniuUtils.upload2qiniu(accessKey, secretKey, bucket, bytes, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        }
        
        log.info("FileServiceImpl.upload end. resultParam: fileUrl={0}", fileName);
        // 返回文件名
        return fileName;
    }

}