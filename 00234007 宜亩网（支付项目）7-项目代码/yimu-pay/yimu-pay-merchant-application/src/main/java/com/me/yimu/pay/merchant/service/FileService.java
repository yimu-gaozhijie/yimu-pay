package com.me.yimu.pay.merchant.service;

import java.sql.BatchUpdateException;

/**
 * 文件服务
 * @author PC
 *
 */
public interface FileService {

    /**
     * 上传文件
     * @param bytes 文件字节
     * @param fileName 文件名称
     * @return 文件下载路径
     * @throws BatchUpdateException
     */
    public String upload(byte[] bytes, String fileName) throws BatchUpdateException;
    
}
