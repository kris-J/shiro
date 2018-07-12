package com.iminer.cas.client;

import com.iminer.cas.remote.RemoteServiceInterface;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/13 18:30
 */
public class ClientSessionDAO extends CachingSessionDAO {

    private RemoteServiceInterface remoteService;
    private String appKey;

    public void setRemoteService(RemoteServiceInterface remoteService) {
        this.remoteService = remoteService;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    @Override
    protected void doDelete(Session session) {
        remoteService.deleteSession(appKey, session);
    }

    @Override
    protected void doUpdate(Session session) {
        remoteService.updateSession(appKey, session);
    }


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = remoteService.createSession(session);
        assignSessionId(session, sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session =remoteService.getSession(appKey, sessionId);
        return session;
    }

}
