package com.fangjie.controller.system;

import com.fangjie.dao.UserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fangjie
 * @Description: ${todo}
 * @date 2018/6/12 17:49
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserDao userDao;

    @RequestMapping("/signin")
    public ModelAndView signin(String username, String password, boolean rememberMe){
        ModelAndView view = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,rememberMe);
        String emsg="";
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            emsg="用户名不存在";
        }catch (IncorrectCredentialsException e) {
            emsg="密码错误";
        }catch (LockedAccountException e) {
            emsg="账号被锁定";
        }catch (AuthenticationException e) {
            emsg="错误："+e.getMessage();
        }

        if(!StringUtils.hasText(emsg)){
            //登录成功，设置session
            Session session = subject.getSession();
            session.setAttribute("key", "234234");
            System.out.println("登录成功，设置session，id="+session.getId());
            view.setViewName("redirect:/user/userInfo");
            return view;
        }
        view.setViewName("/login");
        view.addObject("errorMessage", emsg);
        return view;
    }
}
