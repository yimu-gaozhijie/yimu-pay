package com.me.yimu.pay.merchant.controller;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.me.yimu.pay.domain.BusinessException;
import com.me.yimu.pay.domain.CommonErrorCode;
import com.me.yimu.pay.merchant.api.AppService;
import com.me.yimu.pay.merchant.api.MerchantService;
import com.me.yimu.pay.merchant.api.dto.AppDTO;
import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.merchant.convert.MerchantDetailConvert;
import com.me.yimu.pay.merchant.convert.MerchantRegisterConvert;
import com.me.yimu.pay.merchant.service.FileService;
import com.me.yimu.pay.merchant.service.SmsService;
import com.me.yimu.pay.merchant.util.SecurityUtil;
import com.me.yimu.pay.merchant.vo.MerchantDetailVO;
import com.me.yimu.pay.merchant.vo.MerchantRegisterVO;
import com.me.yimu.pay.utils.PhoneUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(value = "商户平台‐商户相关", tags = "商户平台‐商户相关", description = "商户平台‐商户相关")
@RestController
@Slf4j
public class MerchantController {

    // 注入远程Dubbo接口的Bean
    @org.apache.dubbo.config.annotation.Reference
    MerchantService merchantService;
    
    // 调用的本地的Bean
    // 短信发送
    @Autowired
    SmsService smsService;
    // 文件处理
    @Autowired
    FileService fileService;
    
    @Reference
    private AppService appService;
    
    @ApiOperation(value="根据id查询商户信息")
    @GetMapping("/merchants/{id}")
    public MerchantDTO queryMerchantById(@PathVariable("id") Long id){

        MerchantDTO merchantDTO = merchantService.queryMerchantById(id);
        return merchantDTO;
    }
    
    @ApiOperation("测试Swagger:hello")
    @GetMapping(path = "/hello")
    public String hello() {
        return "hello";
    }
    
    @ApiOperation("测试Swagger:hi")
    @ApiImplicitParam(name = "name", value = "姓名", required = true, dataType = "string")
    @PostMapping(value = "/hi")
    public String hi(String name) {
        return "hi,"+name;
    }
    
    @ApiOperation("获取手机验证码")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, dataType = "String",
    paramType = "query")
    @GetMapping("/sms")
    public String getSMSCode(@RequestParam String phone) {
        log.info("向手机号:{}发送验证码", phone);
        return smsService.sendMsg(phone);
    }
    
    @ApiOperation("注册商户")
    @ApiImplicitParam(name = "merchantRegister", value = "注册信息", 
        required = true, dataType = "MerchantRegisterVO",
        paramType = "body")
    @PostMapping("/merchants/register")
    public MerchantRegisterVO registerMerchant(
            @RequestBody MerchantRegisterVO merchantRegister) {
        // 1.校验
        if (merchantRegister == null) {
            throw new BusinessException(CommonErrorCode.E_100108);
        }
        //手机号非空校验
        if (StringUtils.isBlank(merchantRegister.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100112);
        }
        //校验手机号的合法性
        if (!PhoneUtil.isMatches(merchantRegister.getMobile())) {
            throw new BusinessException(CommonErrorCode.E_100109);
        }
        
        //联系人非空校验
        if (StringUtils.isBlank(merchantRegister.getUsername())) {
            throw new BusinessException(CommonErrorCode.E_100110);
        }
        //密码非空校验
        if (StringUtils.isBlank(merchantRegister.getPassword())) {
            throw new BusinessException(CommonErrorCode.E_100111);
        }
        //验证码非空校验
        if (StringUtils.isBlank(merchantRegister.getVerifiyCode()) ||
            StringUtils.isBlank(merchantRegister.getVerifiykey())) {
            throw new BusinessException(CommonErrorCode.E_100103);
        }
        
        // 校验验证码
        smsService.checkVerifiyCode(merchantRegister.getVerifiykey(), 
                merchantRegister.getVerifiyCode());
        
        // 注册商户
        MerchantDTO merchantDTO = 
                MerchantRegisterConvert.INSTANCE.vo2dto(merchantRegister);
        merchantService.createMerchant(merchantDTO);

        return merchantRegister;
    }
    
    @ApiOperation("证件上传")
    @PostMapping("/upload")
    public String upload(@ApiParam(value = "上传的文件", required = true)
            MultipartFile file) {
        // 上传文件名称
        String originalFilename = file.getOriginalFilename();
        
        // 文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") - 1);
        
        // 文件名称
        String fileName = UUID.randomUUID().toString() + suffix;
        
        // 上传文件，返回文件名
        String upedfileName = null;
        try {
            upedfileName = fileService.upload(file.getBytes(), fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(CommonErrorCode.E_100106);
        } 
        
        return upedfileName;
    }

    @ApiOperation("商户资质申请")
    @ApiImplicitParams({
    @ApiImplicitParam(name = "merchantInfo", value = "商户认证资料", required = true,
    dataType = "MerchantDetailVO", paramType = "body")
    })
    @PostMapping("/my/merchants/save")
    public void saveMerchant(@RequestBody MerchantDetailVO merchantInfo) {
        // 解析token得到商户id
        Long merchantId = SecurityUtil.getMerchantId();
        MerchantDTO merchantDTO = MerchantDetailConvert.INSTANCE.vo2dto(merchantInfo);
        //资质申请
        merchantService.applyMerchant(merchantId,merchantDTO);
    }
    
    @ApiOperation("查询商户下的应用列表")
    @GetMapping(value = "/my/apps")
    public List<AppDTO> queryMyApps() {
        Long merchantId = SecurityUtil.getMerchantId();
        return appService.queryAppByMerchant(merchantId);
    }
    
    @ApiOperation("获取登录用户的商户信息")
    @GetMapping(value="/my/merchants")
    public MerchantDTO getMyMerchantInfo(){
        log.info(this.getClass().getCanonicalName() + ".getMyMerchantInfo START");
        Long merchantId = SecurityUtil.getMerchantId();
        log.info("merchantId" + " is " + merchantId);
        MerchantDTO merchant = merchantService.queryMerchantById(merchantId);
        log.info(this.getClass().getCanonicalName() + ".getMyMerchantInfo END");
        return merchant;
    }
}
