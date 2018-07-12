package com.iminer.cas.service;

import com.iminer.cas.core.PasswordHelper;
import com.iminer.cas.dao.UserDao;
import com.iminer.cas.domain.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/14 10:28
 */
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordHelper passwordHelper;

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public User getUserByName(String username){
        return userDao.getUserByName(username);
    }

    /**
     * 创建用户
     * @param username
     * @param password
     * @return
     */
    public User createUser(String username, String password){
        User user = new User();
        user.setLocked(false);
        user.setOrganizationId(1L);
        user.setUsername(username);
        user.setPassword(password);
        passwordHelper.encryptPassword(user);
        return userDao.createUser(user);
    }
}
