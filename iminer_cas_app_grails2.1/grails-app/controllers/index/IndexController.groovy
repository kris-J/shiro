package index

import org.apache.shiro.SecurityUtils
import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.authz.annotation.RequiresRoles
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.session.mgt.SimpleSession
import org.springframework.beans.factory.annotation.Autowire

/**
 * @Description: ${todo}
 * @author fangjie
 * @date 2018/7/6 16:08
 */
class IndexController {

//    @RequiresRoles("admin")
    def index(){
//        println "所发生的法法法师打发斯蒂芬"
//        render "asdfasdfasdfadfasdfds"
        flash.message = "flash message"
    }

    @RequiresRoles("role1")
//    @RequiresPermissions("index:create")
    def homepage(){
        println "homepage"
        render "homepage"
    }

    @RequiresRoles("role2")
    @RequiresPermissions("organization:*")
    def xx(){
        render "xxxxxxx"
    }

    def ff(){
        render "123123123123"
    }

}
