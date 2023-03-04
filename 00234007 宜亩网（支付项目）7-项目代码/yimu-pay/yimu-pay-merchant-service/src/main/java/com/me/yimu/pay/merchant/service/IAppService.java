package com.me.yimu.pay.merchant.service;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.merchant.api.dto.AppDTO;
import com.me.yimu.pay.merchant.entity.App;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2022-12-05
 */
public interface IAppService extends IService<App> {

    /**
    * 商户下创建应用
    * @return
    */
    AppDTO createApp(Long merchantId, AppDTO app) throws BusinessException;
    
}
