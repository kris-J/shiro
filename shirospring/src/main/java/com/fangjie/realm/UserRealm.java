package com.fangjie.realm;

import com.fangjie.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 17:33
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Map<String, Object> userInfo = userService.getUserByUsername((String) authenticationToken.getPrincipal());
        if (userInfo == null) {
            throw new UnknownAccountException();
        }
        if (Boolean.TRUE.equals(Boolean.valueOf(userInfo.get("locked").toString()))) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.get("username"),
                userInfo.get("password"),
                ByteSource.Util.bytes(userInfo.get("username")),
                getName()
        );

        return authenticationInfo;
    }
}
