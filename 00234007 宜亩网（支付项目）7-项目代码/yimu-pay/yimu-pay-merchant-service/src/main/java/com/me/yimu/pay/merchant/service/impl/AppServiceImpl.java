package com.me.yimu.pay.merchant.service.impl;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.CommonErrorCode;
import com.me.yimu.pay.merchant.api.AppService;
import com.me.yimu.pay.merchant.api.dto.AppDTO;
import com.me.yimu.pay.merchant.convert.AppCovert;
import com.me.yimu.pay.merchant.entity.App;
import com.me.yimu.pay.merchant.entity.Merchant;
import com.me.yimu.pay.merchant.mapper.AppMapper;
import com.me.yimu.pay.merchant.mapper.MerchantMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.me.yimu.pay.merchant.service.IAppService;
import com.me.yimu.pay.utils.RandomUuidUtil;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2022-12-05
 */
@Slf4j
@Service
@Transactional
public class AppServiceImpl implements AppService {
    
    @Autowired
    private AppMapper appMapper;

    @Autowired
    private MerchantMapper merchantMapper;
    
    public AppDTO createApp(Long merchantId, AppDTO app) throws BusinessException {
        //校验商户是否通过资质审核
        Merchant merchant = merchantMapper.selectById(merchantId);
        if (merchant == null) {
            throw new BusinessException(CommonErrorCode.E_200002);
        }
        
        if (!"2".equals(merchant.getAuditStatus())) {
            throw new BusinessException(CommonErrorCode.E_200003);
        }
        
        if(isExistAppName(app.getAppName())){
            throw new BusinessException(CommonErrorCode.E_200004);
        }
        
        //保存应用信息
        app.setAppId(RandomUuidUtil.getUUID());
        app.setMerchantId(merchant.getId());
        App entity = AppCovert.INSTANCE.dto2entity(app);
        appMapper.insert(entity);
        return AppCovert.INSTANCE.entity2dto(entity);
    }
    
    /**
    * 校验应用名是否已被使用
    * @param appName
    * @return
    */
    public Boolean isExistAppName(String appName) {
        Integer count = appMapper.selectCount(new QueryWrapper<App>
        ().lambda().eq(App::getAppName, appName));
        return count.intValue() > 0;
    }

    @Override
    public List<AppDTO> queryAppByMerchant(Long merchantId) throws BusinessException {
    	List<App> apps = appMapper.selectList(new QueryWrapper<App>
    	    ().lambda().eq(App::getMerchantId,merchantId));
    	List<AppDTO> appDTOS = AppCovert.INSTANCE.listentity2dto(apps);
    	return appDTOS;
    }

    @Override
    public AppDTO getAppById(String appId) throws BusinessException {
    	App app = appMapper.selectOne(new QueryWrapper<App>().lambda().eq(App::getAppId, appId));
    	return AppCovert.INSTANCE.entity2dto(app);
    }

    /**
    * 查询应用是否属于某个商户
    *
    * @param appId
    * @param merchantId
    * @return
    */
    @Override
    public Boolean queryAppInMerchant(String appId, Long merchantId) {
        Integer count = appMapper.selectCount(new LambdaQueryWrapper<App>().eq(App::getAppId,
            appId)
            .eq(App::getMerchantId, merchantId));
        return count>0;
    }
}
