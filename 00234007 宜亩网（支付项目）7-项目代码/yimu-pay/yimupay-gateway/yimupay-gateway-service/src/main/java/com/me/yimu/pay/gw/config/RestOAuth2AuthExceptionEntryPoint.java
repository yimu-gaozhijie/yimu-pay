package com.me.yimu.pay.gw.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;

import com.me.yimu.pay.domain.RestResponse;
import com.me.yimu.pay.gw.common.util.HttpUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestOAuth2AuthExceptionEntryPoint extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        RestResponse restResponse = new RestResponse(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
        HttpUtil.writerError(restResponse,response);
    }
}
