package com.iminer.cas.dao;

import com.iminer.cas.domain.App;
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
 * @date 2018/6/29 17:31
 */
public class AppDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public App createApp(final App app) {
        final String sql = "insert into sys_app(name, app_key, app_secret, available) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, app.getName());
                psst.setString(count++, app.getAppKey());
                psst.setString(count++, app.getAppSecret());
                psst.setBoolean(count++, app.getAvailable());
                return psst;
            }
        }, keyHolder);
        app.setId(keyHolder.getKey().longValue());
        return app;
    }

    public App updateApp(App app) {
        final String sql = "update sys_app set name=?, app_key=?, app_secret=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                app.getName(), app.getAppKey(), app.getAppSecret(), app.getAvailable(), app.getId());
        return app;
    }

    public void deleteApp(Long appId) {
        final String sql = "delete from sys_app where id=?";
        jdbcTemplate.update(sql, appId);
    }


    public App findOne(Long appId) {
        final String sql = "select id, name, app_key, app_secret, available from sys_app where id=?";
        List<App> appList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(App.class), appId);
        if (appList.size() == 0) {
            return null;
        }
        return appList.get(0);
    }

    public List<App> findAll() {
        final String sql = "select id, name, app_key, app_secret, available from sys_app";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(App.class));
    }

    public Long findAppIdByAppKey(String appKey) {
        final String sql = "select id from sys_app where app_key=?";
        List<Long> appIdList = jdbcTemplate.queryForList(sql, Long.class, appKey);
        if (appIdList.size() == 0) {
            return null;
        }
        return appIdList.get(0);
    }
}
