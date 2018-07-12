package com.fangjie.service;

import com.fangjie.dao.UserDao;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 17:35
 */
public class UserService {

    @Autowired
    UserDao userDao;

    public Map<String, Object> getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }


    public Boolean createUser(String username, String password) {
        String newPassword = new SimpleHash(
                "md5",
                password,
                ByteSource.Util.bytes(username),
                2
        ).toHex();
        return userDao.createUser(username, newPassword);
    }


}
