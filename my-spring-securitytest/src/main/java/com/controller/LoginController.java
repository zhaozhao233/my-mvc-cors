package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/loginview")
    public String loginView() {
        return "loginview";
    }

    @RequestMapping("/loginsuccess")
    public String loginsuccess() {
        return "loginsuccess";
    }

    @RequestMapping("/loginfail")
    public String loginFail() {
        return "loginfail";
    }
}
