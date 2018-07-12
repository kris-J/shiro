package com.fangjie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 17:35
 */
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Map<String, Object> getUserByUsername(String username) {
        String select_sql = "select * from sys_users where username=?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(select_sql, new Object[]{username});
        return result.size() > 0 ? result.get(0) : null;
    }


    public Boolean createUser(String username, String encryptionPassword) {
        String insert_sql = "insert into sys_users (username,password,salt,locked) values (?,?,?,?)";
        int executeResult = jdbcTemplate.update(insert_sql, new Object[]{username, encryptionPassword, "", false});
        return executeResult > 0 ? true : false;
    }

}
