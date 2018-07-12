package com.iminer.cas.dao;

import com.iminer.cas.domain.Authorization;
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
 * @date 2018/6/29 17:19
 */
public class AuthorizationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Authorization findByAppUser(Long appId, Long userId) {
        final String sql = "select id, user_id, app_id, role_ids as roleIdsStr from sys_user_app_roles where app_id=? and user_id=?";
        List<Authorization> authorizationList = jdbcTemplate.query(sql, new Object[]{appId, userId}, new BeanPropertyRowMapper<>(Authorization.class));
        if (authorizationList.size() == 0) {
            return null;
        }
        return authorizationList.get(0);
    }

    public Authorization createAuthorization(final Authorization authorization) {

        final String sql = "insert into sys_user_app_roles(user_id, app_id, role_ids) values(?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setLong(count++, authorization.getUserId());
                psst.setLong(count++, authorization.getAppId());
                psst.setString(count++, authorization.getRoleIdsStr());
                return psst;
            }
        }, keyHolder);
        authorization.setId(keyHolder.getKey().longValue());
        return authorization;
    }

    public Authorization updateAuthorization(Authorization authorization) {
        final String sql = "update sys_user_app_roles set user_id=?, app_id=?, role_ids=? where id=?";
        jdbcTemplate.update(
                sql,
                authorization.getUserId(), authorization.getAppId(), authorization.getRoleIdsStr(), authorization.getId());
        return authorization;
    }

    public void deleteAuthorization(Long authorizationId) {
        final String sql = "delete from sys_user_app_roles where id=?";
        jdbcTemplate.update(sql, authorizationId);
    }

    public Authorization findOne(Long authorizationId) {
        final String sql = "select id, user_id, app_id, role_ids as roleIdsStr from sys_user_app_roles where id=?";
        List<Authorization> authorizationList = jdbcTemplate.query(sql, new Object[]{authorizationId}, new BeanPropertyRowMapper<>(Authorization.class));
        if (authorizationList.size() == 0) {
            return null;
        }
        return authorizationList.get(0);
    }

    public List<Authorization> findAll() {
        final String sql = "select id, user_id, app_id, role_ids as roleIdsStr from sys_user_app_roles";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Authorization.class));
    }
}
