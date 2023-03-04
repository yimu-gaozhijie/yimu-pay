package com.me.yimu.pay.merchant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.dubbo.config.annotation.Reference;
import com.me.yimu.pay.merchant.api.AppService;
import com.me.yimu.pay.merchant.api.dto.AppDTO;
import com.me.yimu.pay.merchant.util.SecurityUtil;
import com.me.yimu.pay.transaction.api.PayChannelService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author PC
 *
 */
@Api(value = "商户平台‐应用管理", tags = "商户平台‐应用相关", description = "商户平台‐应用相关")
@RestController
public class AppController {

    @Reference
    private AppService appService;
    
    //@Reference
    @org.apache.dubbo.config.annotation.Reference
    private PayChannelService payChannelService;
    
    @ApiOperation("商户创建应用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "app", value = "应用信息", required = true, dataType = "AppDTO", paramType = "body")})
    @PostMapping(value = "/my/apps")
    public AppDTO createApp(@RequestBody AppDTO app){
        //得到商户id
        Long merchantId = SecurityUtil.getMerchantId();

        return  appService.createApp(merchantId,app);
    }

    @ApiOperation("根据appid获取应用的详细信息")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "appId", value = "商户应用id", required = true, dataType = "String", paramType = "path")})
    @GetMapping(value = "/my/apps/{appId}")
    public AppDTO getApp(@PathVariable String appId) {
        return appService.getAppById(appId);
    }
    
//    @ApiOperation("根据appid获取应用的详细信息")
//    @ApiImplicitParams({
//    @ApiImplicitParam(name = "appId", value = "商户应用id", required = true, dataType = "String", paramType = "path")})
//    @GetMapping(value = "/my/apps/{appId}")
//    public AppDTO getApp(@PathVariable String appId) {
//        return appService.getAppById(appId);
//    }
    
    @ApiOperation("绑定服务类型")
    @PostMapping(value="/my/apps/{appId}/platform‐channels")
    @ApiImplicitParams({
    @ApiImplicitParam(value = "应用id",name = "appId",dataType = "string",paramType =
    "path"),
    @ApiImplicitParam(value = "服务类型code",name = "platformChannelCodes",dataType =
    "string",paramType = "query")
    })
    public void bindPlatformForApp(@PathVariable("appId") String appId,
        @RequestParam("platformChannelCodes") String platformChannelCodes){
        payChannelService.bindPlatformChannelForApp(appId,platformChannelCodes);
    }
    
    @ApiOperation("查询应用是否绑定了某个服务类型")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "appId", value = "应用appId", required = true, dataType =
    "String", paramType = "query"),
    @ApiImplicitParam(name = "platformChannel", value = "服务类型", required = true, dataType =
    "String", paramType = "query")
    })
    @GetMapping("/my/merchants/apps/platformchannels")
    public int queryAppBindPlatformChannel(@RequestParam String appId, @RequestParam String
    platformChannel){
        return payChannelService.queryAppBindPlatformChannel(appId,platformChannel);
    }

}