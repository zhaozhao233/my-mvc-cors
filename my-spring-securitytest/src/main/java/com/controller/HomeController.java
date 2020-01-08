package com.controller;

import com.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index() {
        System.out.println("初始化界面.....................");
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(Authentication auth) {
//        String username = ((User)SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getPrincipal()).getUsername();
//        System.out.println("username :"+username);
        return "admin";
    }
}
