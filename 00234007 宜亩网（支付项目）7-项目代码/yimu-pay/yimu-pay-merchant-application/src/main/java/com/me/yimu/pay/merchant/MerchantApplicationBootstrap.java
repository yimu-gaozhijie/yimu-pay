package com.me.yimu.pay.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = {"com.me.yimu.pay.merchant.controller","com.me.yimu.pay.merchant.config",
    "com.me.yimu.pay.merchant.service", "com.me.yimu.pay.merchant.intercept"})
@EnableDiscoveryClient
public class MerchantApplicationBootstrap {
	
    public static void main(String[] args) {
        SpringApplication.run(MerchantApplicationBootstrap.class,args);
        System.out.println("Nacos请求路径：http://127.0.0.1:8848/nacos/");
        System.out.println("七牛云存储空间：https://portal.qiniu.com/kodo/bucket");
        System.out.println("前端启动命令：yarn serve");
    }
    
    /**
     * RestTemplate初始化
     * @return
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
    
}