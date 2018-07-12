package com.iminer.cas.client.bean;

import com.iminer.cas.client.*;
import com.iminer.cas.client.exception.DefaultExceptionHandler;
import com.iminer.cas.remote.RemoteServiceInterface;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.Filter;
import java.util.Map;
import java.util.Properties;

/**
 * @author fangjie
 * @Description: shiro核心配置
 * @date 2018/6/28 10:50
 */
@Configuration
@ImportResource("classpath:client/iminer-cas-client-remote-service.xml")
public class ClientShiroBeanConfig {


    @Bean(name = "remoteRealm")
    public AuthorizingRealm remoteRealm(@Qualifier("remoteService") Object remoteService) {
        ClientRealm clientRealm = new ClientRealm();
        clientRealm.setCachingEnabled(false);
        clientRealm.setAppKey(ClientShiroPropertyPlaceholder.getProperty("client.app.key"));
        clientRealm.setRemoteService((RemoteServiceInterface) remoteService);
        return clientRealm;
    }

    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }

    @Bean(name = "sessionIdCookie")
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(ClientShiroPropertyPlaceholder.getProperty("client.session.id"));
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(Integer.valueOf(ClientShiroPropertyPlaceholder.getProperty("client.session.id.maxage")));
        simpleCookie.setDomain(ClientShiroPropertyPlaceholder.getProperty("client.cookie.domain"));
        simpleCookie.setPath(ClientShiroPropertyPlaceholder.getProperty("client.cookie.path"));
        return simpleCookie;
    }

    @Bean(name = "rememberMeCookie")
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName(ClientShiroPropertyPlaceholder.getProperty("client.rememberMe.id"));
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(Integer.valueOf(ClientShiroPropertyPlaceholder.getProperty("client.remeber.id.maxage")));
        simpleCookie.setDomain(ClientShiroPropertyPlaceholder.getProperty("client.cookie.domain"));
        simpleCookie.setPath(ClientShiroPropertyPlaceholder.getProperty("client.cookie.path"));
        return simpleCookie;
    }

    @Bean(name = "rememberMeManager")
    public CookieRememberMeManager rememberMeManager(@Qualifier("rememberMeCookie") SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCookie(rememberMeCookie);
        return cookieRememberMeManager;
    }

    @Bean(name = "sessionDAO")
    public ClientSessionDAO sessionDAO(@Qualifier("remoteService") Object remoteService) {
        ClientSessionDAO clientSessionDAO = new ClientSessionDAO();
        clientSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        clientSessionDAO.setAppKey(ClientShiroPropertyPlaceholder.getProperty("client.app.key"));
        clientSessionDAO.setRemoteService((RemoteServiceInterface) remoteService);
        return clientSessionDAO;
    }

    @Bean(name = "sessionManager")
    public ClientSessionManager sessionManager(@Qualifier("sessionDAO") ClientSessionDAO sessionDAO,
                                               @Qualifier("sessionIdCookie") SimpleCookie sessionIdCookie) {
        ClientSessionManager sessionManager = new ClientSessionManager();
        sessionManager.setDeleteInvalidSessions(false);
        sessionManager.setSessionValidationSchedulerEnabled(false);
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie);
        return sessionManager;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("remoteRealm") AuthorizingRealm remoteRealm,
                                                     @Qualifier("sessionManager") DefaultWebSessionManager sessionManager,
                                                     @Qualifier("rememberMeManager") CookieRememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(remoteRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    @Bean
    public MethodInvokingFactoryBean invokingFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        MethodInvokingFactoryBean invokingFactoryBean = new MethodInvokingFactoryBean();
        invokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        invokingFactoryBean.setArguments(new Object[]{securityManager});
        return invokingFactoryBean;
    }

    @Bean(name = "shiroFilter")
    public ClientShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ClientShiroFilterFactoryBean shiroFilterFactoryBean = new ClientShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl(ClientShiroPropertyPlaceholder.getProperty("client.login.url"));
        shiroFilterFactoryBean.setSuccessUrl(ClientShiroPropertyPlaceholder.getProperty("client.success.url"));
//        shiroFilterFactoryBean.setUnauthorizedUrl(ClientShiroPropertyPlaceholder.getProperty("client.unauthorized.url"));
        // 自定义过滤器
        Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
        // 注：ClientAuthenticationFilter过滤器并不进行@Bean注入，否则springboot在加载过滤器时，会将此过滤器加载在ShiroFilter过滤器之前
        filterMap.put("authc", new ClientAuthenticationFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setFiltersStr(ClientShiroPropertyPlaceholder.getProperty("client.filters"));
        shiroFilterFactoryBean.setFilterChainDefinitionsStr(ClientShiroPropertyPlaceholder.getProperty("client.filter.chain.definitions"));
        return shiroFilterFactoryBean;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启注解
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor sourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        sourceAdvisor.setSecurityManager(securityManager);
        return sourceAdvisor;
    }

    /**
     * 权限异常处理
     * @return
     */
    @Bean
    public DefaultExceptionHandler unauthorizedExceptionResolver() {
        return new DefaultExceptionHandler();
    }
}
