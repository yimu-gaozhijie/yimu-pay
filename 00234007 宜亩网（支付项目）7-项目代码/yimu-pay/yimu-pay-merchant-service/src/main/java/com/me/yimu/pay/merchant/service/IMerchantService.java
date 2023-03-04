package com.me.yimu.pay.merchant.service;

import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.merchant.entity.Merchant;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2022-12-05
 */
@Service
public interface IMerchantService {

	/**
	* 根据ID查询详细信息
	* @param merchantId
	* @return
	* @throws BusinessException
	*/
	MerchantDTO queryMerchantById(Long merchantId);
	
}
