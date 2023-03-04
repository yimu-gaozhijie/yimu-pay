package com.me.yimu.pay.uaa.config;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.me.yimu.pay.uaa.integration.IntegrationUserDetailsAuthenticationHandler;
import com.me.yimu.pay.uaa.integration.IntegrationUserDetailsAuthenticationProvider;
import com.me.yimu.pay.user.api.AuthorizationService;
import com.me.yimu.pay.user.api.TenantService;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security配置
 */
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Reference
    private TenantService tenantService;
    
    @Reference
    private AuthorizationService authorizationService;

    public SecurityConfig() {
    }

    @Bean
    public IntegrationUserDetailsAuthenticationHandler integrationUserDetailsAuthenticationHandler(){
        IntegrationUserDetailsAuthenticationHandler authenticationHandler = new IntegrationUserDetailsAuthenticationHandler();
        authenticationHandler.setTenantService(tenantService);
        return authenticationHandler;
    }

    @Bean
    public IntegrationUserDetailsAuthenticationProvider integrationUserDetailsAuthenticationProvider(){
        IntegrationUserDetailsAuthenticationProvider provider = new IntegrationUserDetailsAuthenticationProvider(integrationUserDetailsAuthenticationHandler());
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(integrationUserDetailsAuthenticationProvider());
    }
    

    //不定义没有password grant_type
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/public/**", "/webjars/**", "/v2/**", "/swagger**", "/static/**", "/resources/**");
        //web.httpFirewall(new DefaultHttpFirewall());//StrictHttpFirewall 去除验url非法验证防火墙
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info(this.getClass().getCanonicalName() + ".configure START");
        http.authorizeRequests()
            .antMatchers("/login*").permitAll()
            .antMatchers("/logout*").permitAll()
            .antMatchers("/druid/**").permitAll()
            .anyRequest().authenticated()
            .and().formLogin() // 允许表单登录
                .loginPage("/login") // 登录页面
                .loginProcessingUrl("/login.do") // 登录处理url
                .failureUrl("/login?authentication_error=1")
                .defaultSuccessUrl("/oauth/authorize")
                .usernameParameter("username")
                .passwordParameter("password")
            .and().logout()
                .logoutUrl("/logout.do")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
            .and().csrf().disable()
            .exceptionHandling()
            .accessDeniedPage("/login?authorization_error=2");
        log.info(this.getClass().getCanonicalName() + ".configure END");
    }

}
