import org.apache.shiro.authz.annotation.RequiresPermissions
import org.apache.shiro.authz.annotation.RequiresRoles

import javax.security.auth.Subject

/**
 * @Description: ${todo}
 * @author fangjie
 * @date 2018/6/15 16:37
 */
class IndexController {

    @RequiresRoles("admin")
    def index(){
        println "所发生的法法法师打发斯蒂芬"
        flash.message = "grails3 flash message"
        [a:'sss']
//        render "asdfasdfasdfadfasdfds"
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

}
