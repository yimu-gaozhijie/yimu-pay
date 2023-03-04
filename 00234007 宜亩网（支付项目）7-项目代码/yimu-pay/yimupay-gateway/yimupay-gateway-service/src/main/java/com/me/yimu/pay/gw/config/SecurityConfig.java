package com.me.yimu.pay.gw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info(this.getClass().getCanonicalName() + ".configure START");
        http.authorizeRequests()
                .antMatchers("/**").permitAll() // 所有的请求都可以访问
                .and().csrf().disable();
        log.info(this.getClass().getCanonicalName() + ".configure END");
    }
    
}