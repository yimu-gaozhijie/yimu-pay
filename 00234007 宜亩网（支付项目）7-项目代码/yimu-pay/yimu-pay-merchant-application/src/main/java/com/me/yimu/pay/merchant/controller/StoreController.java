package com.me.yimu.pay.merchant.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.me.yimu.pay.domain.PageVO;
import com.me.yimu.pay.merchant.api.MerchantService;
import com.me.yimu.pay.merchant.api.dto.StoreDTO;
import com.me.yimu.pay.merchant.util.SecurityUtil;

/**
 * 门店管理相关接口定义
 * @author Administrator
 * @version 1.0
 **/
@Api(value = "商户平台-门店管理", tags = "商户平台-门店管理", description = "商户平台-门店的增删改查")
@RestController
@Slf4j
public class StoreController {

    @Reference
    MerchantService merchantService;

    @ApiOperation("门店列表查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNo",value = "页码",required = true,dataType = "int",paramType = "query"),
            @ApiImplicitParam(name="pageSize",value = "每页记录数",required = true,dataType = "int",paramType = "query")
    })
    @PostMapping("/my/stores/merchants/page")
    public PageVO<StoreDTO> queryStoreByPage(Integer pageNo,Integer pageSize){
        //商户id
        Long merchantId = SecurityUtil.getMerchantId();
        //查询条件
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setMerchantId(merchantId);//商户id
        //调用service查询列表
        PageVO<StoreDTO> storeDTOS = merchantService.queryStoreByPage(storeDTO, pageNo, pageSize);
        return storeDTOS;
    }
}
