package com.fangjie.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 10:06
 */
public class HelloWorldTest {


    public void login(String configFile, String username, String password) {
        //创建一个SecurityManager工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(configFile);
        SecurityManager securityManager = factory.getInstance();
        //securityManager绑定到SecurityUtils，全局设置
        SecurityUtils.setSecurityManager(securityManager);
        //通过SecurityUtils得到Subject，其会自动绑定到当前线程；如果在web环境在请求结束时需要解除绑定
        Subject subject = SecurityUtils.getSubject();
        //根据用户名、密码获取token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //登录验证，委托给securityManager
        subject.login(token);
    }


    @Test
    public void test() {
        try {
            login("classpath:shiro.ini", "zhang", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }
        Subject subject = SecurityUtils.getSubject();

        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        subject.logout();
    }

    @Test
    public void realmTest() {
        try {
            login("classpath:shiro-realm.ini", "zhang", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }
        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        subject.logout();
    }

    @Test
    public void jdbcRealmTest() {
        try {
            login("classpath:shiro-jdbc-realm.ini", "zhang", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }

        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        subject.logout();
    }

    @Test
    public void roleTest() {
        try {
            login("classpath:shiro-role.ini", "zhang", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }

        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        Assert.assertEquals(true, subject.hasRole("role3"));
        subject.logout();

    }

    @Test
    public void permissionTest() {
        try {
            login("classpath:shiro-permission.ini", "wang", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }

        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        Assert.assertEquals(true, subject.isPermitted("user:create"));
        Assert.assertEquals(true, subject.isPermitted("user:delte"));
        subject.logout();
    }

    @Test
    public void passwordServiceTest(){
        try {
            login("classpath:shiro-passwordservice.ini", "liu", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }

        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        subject.logout();
    }

    @Test
    public void passwordServiceJDBCTest() {
        try {
            login("classpath:shiro-jdbc-passwordservice.ini", "zhang", "123");
        } catch (LockedAccountException e) {
            System.out.println("账号被锁定");
        } catch (DisabledAccountException e) {
            System.out.println("账号被禁用");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误");
        } catch (AuthenticationException e) {
            System.out.println("验证失败");
        }

        Subject subject = SecurityUtils.getSubject();
        Assert.assertEquals(true, subject.isAuthenticated());//断言用户是否登录
        subject.logout();
    }

}
