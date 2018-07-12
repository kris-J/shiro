package com.iminer.cas.dao;

import com.iminer.cas.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/14 10:28
 */
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    public User getUserByName(String username) {
        String select_sql = "select * from sys_user where username=?";
        List<User> userInfo = jdbcTemplate.query(select_sql, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        if (userInfo.size() > 0) {
            return userInfo.get(0);
        } else {
            return null;
        }
    }

    /**
     * 创建用户
     *
     * @param user
     * @return
     */
    public User createUser(final User user) {
        final String insert_sql = "insert into sys_user (organization_id,username,password,salt,locked) values (?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(insert_sql, new String[]{"id"});
                int count = 1;
                psst.setLong(count++, user.getOrganizationId());
                psst.setString(count++, user.getUsername());
                psst.setString(count++, user.getPassword());
                psst.setString(count++, user.getSalt());
                psst.setBoolean(count++, user.getLocked());
                return psst;
            }
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

}
