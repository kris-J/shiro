package iminer_cas_app_grails

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.web.filter.DelegatingFilterProxy

class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application, args)
    }

    @Bean
    public FilterRegistrationBean shiroFilterBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean()
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"))
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true")
        filterRegistration.setEnabled(true)
        // 可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        filterRegistration.addUrlPatterns("/*")
        return filterRegistration
    }
}