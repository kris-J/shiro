package com.iminer.cas.controller;

import com.iminer.cas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String index() {
        return "/register";
    }

    @RequestMapping(value = "/toRegister", method = RequestMethod.POST)
    public String toRegister(String username, String password) {
        userService.createUser(username, password);
        return "/login";
    }
}
