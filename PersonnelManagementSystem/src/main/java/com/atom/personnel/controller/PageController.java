package com.atom.personnel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "forward:register.html";
    }

    @GetMapping("/index")
    public String index() {
        return "forward:/index.html";
    }

    @GetMapping("/404")
    public String notFound() {
        return "forward:/404.html";
    }
}
