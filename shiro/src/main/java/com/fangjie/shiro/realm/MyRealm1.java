package com.fangjie.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 10:53
 */
public class MyRealm1 implements Realm {

    //返回唯一的realm名称
    @Override
    public String getName() {
        return "myRealm1";
    }

    //判断realm是否支持此token
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        //仅支持UsernamePasswordToken类型的token
        return authenticationToken instanceof UsernamePasswordToken;
    }

    //根据token获取认证信息
    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();//用户名
        String password = new String((char[]) authenticationToken.getCredentials());//密码
        if (!"zhang".equals(username)) {//用户名错误
            throw new UnknownAccountException();
        }
        if (!"123".equals(password)) {//密码错误
            throw new IncorrectCredentialsException();
        }
        //认证成功，返回SimpleAuthenticationInfo实现
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
