package com.iminer.cas.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/14 9:42
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @RequestMapping("/homePage")
    @RequiresRoles("role2")
    public String homePage(){
        return "/index/homePage";
    }

    @RequestMapping("/menu")
    public String menu(){
        return "/index/menu";
    }

}
