class GlobalInterceptor {


    int order = HIGHEST_PRECEDENCE

    /**
     * 构造，定义匹配规则
     */
    GlobalInterceptor() {
        matchAll()
    }

    boolean before() {
//        def sessionUser = session.getAttribute("user")
//        //未登录且请求非登录页面
//        if(sessionUser == null && !"login".equals(params.controller) && !"index".equals(params.action) ){
//            redirect(controller:'login', action:'index')
//            return false
//        }
        println controllerName + "/" + actionName
        return true
    }


}
