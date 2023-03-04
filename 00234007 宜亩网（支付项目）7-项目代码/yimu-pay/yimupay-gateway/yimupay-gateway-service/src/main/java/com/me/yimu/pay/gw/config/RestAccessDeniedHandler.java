package com.me.yimu.pay.gw.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.me.yimu.pay.domain.RestResponse;
import com.me.yimu.pay.gw.common.util.HttpUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAccessDeniedHandler  implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex) throws IOException, ServletException {
        RestResponse restResponse = new RestResponse(HttpStatus.FORBIDDEN.value(),"没有权限");
        HttpUtil.writerError(restResponse,response);
    }
}
