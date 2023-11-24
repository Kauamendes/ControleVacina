package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.LoginDto;
import com.example.demo.services.LoginService;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private LoginService service;

    @PostMapping
    public ModelAndView login(LoginDto loginDto, HttpSession session) {
        return service.findByAccess(loginDto, session);
    }

    @GetMapping
    public String telaLogin() {
        return "login";
    }
}
