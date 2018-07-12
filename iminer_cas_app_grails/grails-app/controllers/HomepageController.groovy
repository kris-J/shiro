import org.apache.shiro.authz.annotation.RequiresRoles

/**
 * @Description: ${todo}
 * @author fangjie
 * @date 2018/7/11 16:51
 */
class HomepageController {
    @RequiresRoles("role1")
    def index(){
        flash.message = "ffff"
//        [a:'11']
    }
}
