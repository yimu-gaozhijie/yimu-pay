package com.me.yimu.pay.transaction.service.impl;

import java.util.List;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.me.yimu.pay.cache.Cache;
import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.CommonErrorCode;
import com.me.yimu.pay.transaction.api.PayChannelService;
import com.me.yimu.pay.transaction.api.dto.PayChannelDTO;
import com.me.yimu.pay.transaction.api.dto.PayChannelParamDTO;
import com.me.yimu.pay.transaction.api.dto.PlatformChannelDTO;
import com.me.yimu.pay.transaction.convert.PayChannelParamConvert;
import com.me.yimu.pay.transaction.convert.PlatformChannelConvert;
import com.me.yimu.pay.transaction.entity.AppPlatformChannel;
import com.me.yimu.pay.transaction.entity.PayChannelParam;
import com.me.yimu.pay.transaction.entity.PlatformChannel;
import com.me.yimu.pay.transaction.mapper.AppPlatformChannelMapper;
import com.me.yimu.pay.transaction.mapper.PayChannelParamMapper;
import com.me.yimu.pay.transaction.mapper.PlatformChannelMapper;
import com.me.yimu.pay.utils.RedisUtil;

@Service
public class PayChannelServiceImpl implements PayChannelService {

    @Autowired
    private PlatformChannelMapper platformChannelMapper;
    
    @Autowired
    private AppPlatformChannelMapper appPlatformChannelMapper;
    
    @Autowired
    private PayChannelParamMapper payChannelParamMapper;
    
    @Autowired
    Cache cache;
    
    @Override
    public List<PlatformChannelDTO> queryPlatformChannel() throws BusinessException {
        List<PlatformChannel> platformChannels = platformChannelMapper.selectList(null);
        List<PlatformChannelDTO> platformChannelDTOS =
        PlatformChannelConvert.INSTANCE.listentity2listdto(platformChannels);
        return platformChannelDTOS;
    }

    @Override
    @Transactional
    public void bindPlatformChannelForApp(String appId, String platformChannelCodes) throws
    BusinessException {
        //根据appId和平台服务类型code查询app_platform_channel
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new
        LambdaQueryWrapper<AppPlatformChannel>()
        .eq(AppPlatformChannel::getAppId, appId)
        .eq(AppPlatformChannel::getPlatformChannel, platformChannelCodes));
        //如果没有绑定则绑定
        if (appPlatformChannel ==null) {
           appPlatformChannel = new AppPlatformChannel();
           appPlatformChannel.setAppId(appId);
           appPlatformChannel.setPlatformChannel(platformChannelCodes);
           appPlatformChannelMapper.insert(appPlatformChannel);
        }
    }

    @Override
    public int queryAppBindPlatformChannel(String appId, String platformChannel) {
        int count = appPlatformChannelMapper.selectCount(
        new QueryWrapper<AppPlatformChannel>().lambda().eq(AppPlatformChannel::getAppId, appId)
            .eq(AppPlatformChannel::getPlatformChannel, platformChannel));
        //已存在绑定关系返回1
        if (count > 0) {
            return 1;
        } else {
        return 0;
        }
    }
    
    @Override
    public List<PayChannelDTO> queryPayChannelByPlatformChannel(String platformChannelCode) {
        return platformChannelMapper.selectPayChannelByPlatformChannel(platformChannelCode);
    }
    
    /**
     * 支付渠道参数配置
     *
     * @param payChannelParam 配置支付渠道参数：包括：商户id、应用id，服务类型code，支付渠道code，配置名称，配置参数(json)
     * @throws BusinessException
     */
    @Override
    public void savePayChannelParam(PayChannelParamDTO payChannelParam) throws BusinessException {

        if(payChannelParam == null || payChannelParam.getChannelName() == null || payChannelParam.getParam()== null){
            throw new BusinessException(CommonErrorCode.E_300009);
        }
        //根据应用、服务类型、支付渠道查询一条记录
        //根据应用、服务类型查询应用与服务类型的绑定id
        Long appPlatformChannelId = selectIdByAppPlatformChannel(payChannelParam.getAppId(), payChannelParam.getPlatformChannelCode());
       if(appPlatformChannelId == null){
           throw new BusinessException(CommonErrorCode.E_300010);
       }
        //根据应用与服务类型的绑定id和支付渠道查询PayChannelParam的一条记录
        PayChannelParam entity = payChannelParamMapper.selectOne(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId)
                .eq(PayChannelParam::getPayChannel, payChannelParam.getPayChannel()));
        //如果存在配置则更新
        if(entity != null){
            entity.setChannelName(payChannelParam.getChannelName());//配置名称
            entity.setParam(payChannelParam.getParam());//json格式的参数
            payChannelParamMapper.updateById(entity);
        }else{
            //否则添加配置
            PayChannelParam entityNew = PayChannelParamConvert.INSTANCE.dto2entity(payChannelParam);
            entityNew.setId(null);
            entityNew.setAppPlatformChannelId(appPlatformChannelId);//应用与服务类型绑定关系id
            payChannelParamMapper.insert(entityNew);
        }

        //保存到redis
        updateCache(payChannelParam.getAppId(),payChannelParam.getPlatformChannelCode());
    }
    
    /**
     * 根据应用、服务类型查询应用与服务类型的绑定id
     * @param appId
     * @param platformChannelCode
     * @return
     */
    private Long selectIdByAppPlatformChannel(String appId,String platformChannelCode){
        AppPlatformChannel appPlatformChannel = appPlatformChannelMapper.selectOne(new LambdaQueryWrapper<AppPlatformChannel>().eq(AppPlatformChannel::getAppId, appId)
                .eq(AppPlatformChannel::getPlatformChannel, platformChannelCode));
        if(appPlatformChannel!=null){
            return appPlatformChannel.getId();//应用与服务类型的绑定id
        }
        return null;
    }
    
    /**
     * 根据应用和服务类型将查询到支付渠道参数配置列表写入redis
     * @param appId 应用id
     * @param platformChannelCode 服务类型code
     */
    private void updateCache(String appId,String platformChannelCode){

        //得到redis中key(付渠道参数配置列表的key)
        //格式：SJ_PAY_PARAM:应用id:服务类型code，例如：SJ_PAY_PARAM：ebcecedd-3032-49a6-9691-4770e66577af：shanju_c2b
        String redisKey = RedisUtil.keyBuilder(appId, platformChannelCode);
        //根据key查询redis
        Boolean exists = cache.exists(redisKey);
        if(exists){
            cache.del(redisKey);
        }
        //根据应用id和服务类型code查询支付渠道参数
        //根据应用和服务类型找到它们绑定id
        Long appPlatformChannelId = selectIdByAppPlatformChannel(appId, platformChannelCode);
        if(appPlatformChannelId != null){
            //应用和服务类型绑定id查询支付渠道参数记录
            List<PayChannelParam> payChannelParams = payChannelParamMapper.selectList(new LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId, appPlatformChannelId));
            List<PayChannelParamDTO> payChannelParamDTOS = PayChannelParamConvert.INSTANCE.listentity2listdto(payChannelParams);
            //将payChannelParamDTOS转成json串存入redis
            cache.set(redisKey, JSON.toJSON(payChannelParamDTOS).toString());
        }
    }
    
    /**
    * 查询支付渠道参数
    *
    * @param appId 应用id
    * @param platformChannel 服务类型代码
    * @return
    */
    @Override
    public List<PayChannelParamDTO> queryPayChannelParamByAppAndPlatform(String appId, String platformChannel) {
        // 从缓存查询
        //1.key的构建 如：SJ_PAY_PARAM:b910da455bc84514b324656e1088320b:shanju_c2b
        String redisKey = RedisUtil.keyBuilder(appId, platformChannel);
        //是否有缓存
        Boolean exists = cache.exists(redisKey);
        if(exists){
        //从redis获取key对应的value
        String value = cache.get(redisKey);
        //将value转成对象
        List<PayChannelParamDTO> paramDTOS = JSONObject.parseArray(value,
        PayChannelParamDTO.class);
        return paramDTOS;
        }
        //查出应用id和服务类型代码在app_platform_channel主键
        Long appPlatformChannelId = selectIdByAppPlatformChannel(appId,platformChannel);
        //根据appPlatformChannelId从pay_channel_param查询所有支付参数
        List<PayChannelParam> payChannelParams = payChannelParamMapper.selectList(new
        LambdaQueryWrapper<PayChannelParam>().eq(PayChannelParam::getAppPlatformChannelId,
        appPlatformChannelId));
        return PayChannelParamConvert.INSTANCE.listentity2listdto(payChannelParams);
    }
    
    @Override
    public PayChannelParamDTO queryParamByAppPlatformAndPayChannel(String appId, String
    platformChannel,
    String payChannel) throws
    BusinessException {
    List<PayChannelParamDTO> payChannelParamDTOS = queryPayChannelParamByAppAndPlatform(appId,
    platformChannel);
    for(PayChannelParamDTO payChannelParam:payChannelParamDTOS){
    if(payChannelParam.getPayChannel().equals(payChannel)){
    return payChannelParam;
    }
    }
    return null;
    }
}
