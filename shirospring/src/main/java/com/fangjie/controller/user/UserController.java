package com.fangjie.controller.user;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 17:50
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/userInfo")
    @RequiresRoles("admin")
    public ModelAndView userInfo(){
        ModelAndView view = new ModelAndView();
        view.setViewName("/user/list");
        return view;
    }
}
