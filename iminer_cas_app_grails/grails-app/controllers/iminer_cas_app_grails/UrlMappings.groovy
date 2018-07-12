package iminer_cas_app_grails

import org.apache.shiro.authz.UnauthorizedException

import java.lang.reflect.InvocationTargetException

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
