package com.me.yimu.pay.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.me.yimu.pay.merchant.api.MerchantService;
import com.me.yimu.pay.merchant.api.dto.MerchantDTO;
import com.me.yimu.pay.utils.EncryptUtil;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Administrator
 * @version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TokenTemp {

    @Autowired
    MerchantService merchantService;

    @Test
    public void createTestToken() {
        Long merchantId = 1209826678635798530L;//填写用于测试的商户id
        MerchantDTO merchantDTO = merchantService.queryMerchantById(merchantId);
        JSONObject token = new JSONObject();
        token.put("mobile", merchantDTO.getMobile());
        token.put("user_name", merchantDTO.getUsername());
        token.put("merchantId", merchantId);

        String jwt_token = "Bearer " + EncryptUtil.encodeBase64(JSON.toJSONString(token).getBytes());
        log.info(jwt_token);
        System.out.println(jwt_token);
    }
}
