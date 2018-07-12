package com.fangjie.controller.system;

import com.fangjie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/13 9:42
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @RequestMapping("/index")
    public String index(){
        return "/register";
    }

    @RequestMapping("/toRegister")
    public String toRegister(String username,String password){
        if(userService.createUser(username,password)){
            return "/login";
        }else{
            return "redirect:/register/index";
        }
    }
}
