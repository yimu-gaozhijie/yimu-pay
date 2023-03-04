package com.me.yimu.pay.merchant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 * @version 1.0
 **/
@SpringBootApplication(scanBasePackages = "com.me.yimu.pay.merchant")
@MapperScan("com.me.yimu.pay.merchant.mapper")
public class MerchantBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(MerchantBootstrap.class,args);
        System.out.println("Nacos请求路径：http://127.0.0.1:8848/nacos/");
    }
}
