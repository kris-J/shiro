package com.iminer.cas.filter;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author fangjie
 * @Description: 自定义身份验证过滤器
 * @date 2018/6/14 15:02
 */
public class ServerFormAuthenticationFilter extends FormAuthenticationFilter {

    /**
     * 认证成功处理回调
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
        //从session中获取回调url
        String fallbackUrl = (String) getSubject(request, response).getSession().getAttribute("authc.fallbackUrl");
        //如果回调为空，则使用配置指定的successUrl
        if (StringUtils.isEmpty(fallbackUrl)) {
            fallbackUrl = getSuccessUrl();
        }
        WebUtils.redirectToSavedRequest(request, response, fallbackUrl);
    }
}
