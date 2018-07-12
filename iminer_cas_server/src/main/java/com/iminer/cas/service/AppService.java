package com.iminer.cas.service;

import com.iminer.cas.dao.AppDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/29 17:26
 */
public class AppService {

    @Autowired
    private AppDao appDao;

    public Long findAppIdByAppKey(String appKey) {
        return appDao.findAppIdByAppKey(appKey);
    }
}
