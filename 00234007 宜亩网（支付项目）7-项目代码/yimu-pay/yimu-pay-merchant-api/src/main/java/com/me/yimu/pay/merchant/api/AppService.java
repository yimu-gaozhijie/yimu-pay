package com.me.yimu.pay.merchant.api;


import java.util.List;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.merchant.api.dto.AppDTO;

/**
 * 应用管理相关的接口
 * Created by Administrator.
 */
public interface AppService {

    /**
     * 创建应用
     * @param merchantId 商户id
     * @param appDTO 应用信息
     * @return 创建成功的应用信息
     * @throws BusinessException
     */
    AppDTO createApp(Long merchantId,AppDTO appDTO) throws BusinessException;

    /**
     * 根据商户id查询应用列表
     * @param merchantId
     * @return
     * @throws BusinessException
     */
    List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException;

    /**
     * 根据应用id查询应用信息
     * @param appId
     * @return
     * @throws BusinessException
     */
    AppDTO getAppById(String appId)throws BusinessException;
    
    /**
    * 查询应用是否属于某个商户
    * @param appId
    * @param merchantId
    * @return
    */
    Boolean queryAppInMerchant(String appId, Long merchantId);

}