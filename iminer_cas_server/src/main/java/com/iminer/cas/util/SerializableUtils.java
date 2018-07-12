package com.iminer.cas.util;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/13 18:30
 */
public class SerializableUtils {

    /**
     * 序列化
     *
     * @param session
     * @return
     */
    public static String serialize(Session session) {
        try {
            /**
             * 此处进行兼容处理
             * 循环session数据，将org.codehaus.groovy.grails.FLASH_SCOPE进行移除
             * 在grails2版本中，会将flash的信息放在session中，如果与grails3版本公用一个session，会在反序列化过程中抛出找不到类的异常
             */
            Collection<Object> keys = session.getAttributeKeys();
            for (Object key : keys) {
                if (key.toString().indexOf("org.codehaus.groovy.grails.FLASH_SCOPE") > -1) {
                    session.removeAttribute("org.codehaus.groovy.grails.FLASH_SCOPE");
                }
            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }

    /**
     * 反序列化
     *
     * @param sessionStr
     * @return
     */
    public static Session deserialize(String sessionStr) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(sessionStr));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Session) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }
}
